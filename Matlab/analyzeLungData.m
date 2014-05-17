function [ data ] = analyzeLungData( filepath )
% Extract sound data and sampling frequency
[wave,fs] = audioread(filepath);
wave = wave(:,1);

% Sampling range (time in seconds)
t = (0:length(wave)-1)/fs;

% Square amplitude (smoothed)
smoothed_power = smooth(wave.^2, 501, 'loess');

% Sampling range (frequency in Hz)
n = length(wave) - 1;
f = 0:fs/n:fs;
% FFT of audio singal
wavefft = abs(fft(wave));
% Smooth FFT
smoothed_wavefft = smooth(wavefft, 201, 'loess');

% Indexes of frequencies less than 200 Hz
LF_indexes = find(f>100 & f<200);
% Frequencies in low frequency range
LF_f = f(LF_indexes);
% FFT signal in low frequency range
LF_wavefft = smoothed_wavefft(LF_indexes);
% Maximum signal in low frequency range
LF_index_max = find(LF_wavefft == max(LF_wavefft));
LF_max = LF_wavefft(LF_index_max);
% Normalized low frequency domin
LF_norm_wavefft = LF_wavefft / LF_max;

% Indexes of frequencies 200 Hz - 2000 Hz
MF_indexes = find(f>200 & f<800);
% Frequencies in mid frequency range
MF_f = f(MF_indexes);
% FFT signal in mid frequency range
MF_wavefft = smoothed_wavefft(MF_indexes);
% Maximum signal in mid frequency range
MF_index_max = find(MF_wavefft == max(MF_wavefft));
MF_max = MF_wavefft(MF_index_max);
% Normalized mid frequency domin
MF_norm_wavefft = MF_wavefft / MF_max;

% Indexes of frequencies 200 Hz - 2000 Hz
HF_indexes = find(f>800 & f<2000);
% Frequencies in mid frequency range
HF_f = f(HF_indexes);
% FFT signal in mid frequency range
HF_wavefft = smoothed_wavefft(HF_indexes);
% Maximum signal in mid frequency range
HF_index_max = HF_wavefft == max(HF_wavefft);
HF_max = HF_wavefft(HF_index_max);
% Normalized mid frequency domin
HF_norm_wavefft = HF_wavefft / HF_max;

% ANALYTICS
avg_low = mean(LF_wavefft);
avg_med = mean(MF_wavefft);
avg_high = mean(HF_wavefft);
% avg_high = mean(wavefft(high_frequency_indecies));
normf_avg_low = mean(LF_norm_wavefft);
normf_avg_med = mean(MF_norm_wavefft);
normf_avg_high = mean(HF_norm_wavefft);
max_low = LF_f(LF_index_max);
max_med = MF_f(MF_index_max);
max_high = HF_f(HF_index_max);
% max_high = max(wavefft(high_frequency_indecies));
wheeze_estimate = CountWheezes( wave, t, fs );

% OUTPUT
data = [max_low, max_med, ... % Frequency of max signal
        LF_max/avg_low, MF_max/avg_med, ... % Ratio max signal to avg signal
        LF_max/MF_max, HF_max/MF_max, ... % Ratio of range maxes
        mean(LF_wavefft)/mean(MF_wavefft), mean(HF_wavefft)/mean(MF_wavefft), ... % Ratio of range avgs
        normf_avg_low, normf_avg_med, normf_avg_high % Normalzed frequency average
        wheeze_estimate]; % Quantity of wheezes detected
end

