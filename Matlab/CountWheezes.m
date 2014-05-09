function [ count ] = CountWheezes( wave, t, fs )
    count = 0;
    step_size = find(t > 0.15, 1, 'first');

    for i = 1:step_size:length(t)-2*step_size
        lower = i;
        higher = i + 2 * step_size;

        wave_c = wave(lower:higher);

        n_c = length(wave_c) - 1;
        f_tmp = 0:fs/n_c:fs;
        cuttoff_indexes = find(f_tmp>100 & f_tmp<600);
        f_c = f_tmp(cuttoff_indexes);
        wavefft_tmp = abs(fft(wave_c));
        wavefft_c = wavefft_tmp(cuttoff_indexes);
        smoothed_fft = smooth(wavefft_c, 25, 'loess');
        
        max_idx = find(max(smoothed_fft) == smoothed_fft);
        low_freq = f_c(max_idx) - 80;
        high_freq = f_c(max_idx) + 80;
        low_idx = find(f_c > low_freq, 1, 'first');
        high_idx = find(f_c > high_freq, 1, 'first');
        if isempty(low_idx)
            low_idx = 0;
        end
        if isempty(high_idx)
            high_idx = length(smoothed_fft);
        end               
        low_val = smoothed_fft(smoothed_fft == min(smoothed_fft(low_idx:max_idx)));
        low_val = low_val(1);
        high_val = smoothed_fft(smoothed_fft == min(smoothed_fft(max_idx:high_idx)));
        high_val = high_val(1);
        max_val = max(smoothed_fft);
        max_val = max_val(1);
        pk_over_threshold = f_c(wavefft_c == max(wavefft_c)) > 200;
        pk_over_threshold = pk_over_threshold(1);
        if (pk_over_threshold) && (low_val/max_val<0.2) && (high_val/max_val<0.2)
            count = count + 1;
%             low_val/max_val
%             high_val/max_val
%             plot(f_c, wavefft_c, f_c, smoothed_fft);
%             break
        end
    end  
end

