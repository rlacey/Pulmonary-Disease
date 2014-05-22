function [ count ] = CountCrackles( wave, t, fs )
    count = 0;
    step_size = find(t > 0.02, 1, 'first');
    
    n = length(wave) - 1;
    f = 0:fs/n:fs;
    f_c = f>100 & f<2000;
    
    % Cut out signal not within 100 - 2000 Hz range
    wavefft = fft(abs(wave));
    wavefft_c = zeros(1, length(wavefft));        
    wavefft_c(f_c) = wavefft(f_c);
    
    wave_c = abs(ifft(wavefft_c));
    
    for i = 1:step_size:length(t)-2*step_size
        lower = i;
        higher = i + 2 * step_size;
        % If peak is 3.6x greater than surronding signal in 40ms span and
        % at least 3/4 amplitude of max signal in whole sound data
        if (max(wave_c(lower:higher)) > 3.6*mean(wave_c(lower:higher))) &&...
                (max(wave_c(lower:higher)) > 0.75*max(wave_c))
            count = count + 1;  
        end
    end  
end

