function [ data ] = extractWheezeHighFeatures( filepath )
% Extract sound data and sampling frequency
[wave,fs] = audioread(filepath);
wave = wave(:,1);

% Sampling range (time in seconds)
t = (0:length(wave)-1)/fs;

% Sampling range (frequency in Hz)
n = length(wave) - 1;
f = 0:fs/n:fs;
% FFT of audio singal
wavefft = abs(fft(wave));
% Smooth FFT
smoothed_wavefft = smooth(wavefft, 201, 'loess');

% Indexes of frequencies less than 250 Hz
LF_indexes = f>100 & f<250;
% FFT signal in low frequency range
LF_wavefft = smoothed_wavefft(LF_indexes);
% Maximum signal in low frequency range
LF_index_max = (LF_wavefft == max(LF_wavefft));
LF_max = LF_wavefft(LF_index_max);
% Normalized low frequency domin
LF_norm_wavefft = LF_wavefft / LF_max;

% Indexes of frequencies 250 Hz - 2000 Hz
MF_indexes = f>250 & f<800;
% Frequencies in mid frequency range
MF_f = f(MF_indexes);
% FFT signal in mid frequency range
MF_wavefft = smoothed_wavefft(MF_indexes);
% Maximum signal in mid frequency range
MF_index_max = find(MF_wavefft == max(MF_wavefft));
MF_max = MF_wavefft(MF_index_max);
% Normalized mid frequency domin
MF_norm_wavefft = MF_wavefft / MF_max;

% Indexes of frequencies 800 Hz - 2000 Hz
HF_indexes = f>800 & f<2000;
% FFT signal in high frequency range
HF_wavefft = smoothed_wavefft(HF_indexes);
% Maximum signal in high frequency range
HF_index_max = (HF_wavefft == max(HF_wavefft));
HF_max = HF_wavefft(HF_index_max);
% Normalized high frequency domin
HF_norm_wavefft = HF_wavefft / HF_max;

% ANALYTICS
avg_med = mean(MF_wavefft);
normf_avg_low = mean(LF_norm_wavefft);
normf_avg_med = mean(MF_norm_wavefft);
normf_avg_high = mean(HF_norm_wavefft);
max_med = MF_f(MF_index_max);
wheeze_estimate = CountWheezes( wave, t, fs );

% OUTPUT
data = [max_med, ... % Frequency of max signal
        MF_max/avg_med, ... % Ratio max signal to avg signal
        mean(LF_wavefft)/mean(MF_wavefft), mean(HF_wavefft)/mean(MF_wavefft), ... % Ratio of range avgs
        normf_avg_low, normf_avg_med, normf_avg_high, ... % Normalzed frequency average
        wheeze_estimate]; % Quantity of wheezes detected
end

