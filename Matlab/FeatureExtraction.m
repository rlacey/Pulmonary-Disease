% FILE USED FOR TESTING NEW FEATURE EXTRACTION PARAMETERS
% USEFUL GRAPHS INCLUDED FOR TIME AND FREQUNCY DOMAINS
close all;

% Extract sound data and sampling frequency
[wave,fs] = audioread('Sounds/wheeze6.mp3');
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
HF_index_max = find(HF_wavefft == max(HF_wavefft));
HF_max = HF_wavefft(HF_index_max);
% Normalized mid frequency domin
HF_norm_wavefft = HF_wavefft / HF_max;

% PLOT Time vs Amplitude
subplot(2,2,1);
plot(t, wave);
title('Waveform');
xlabel('Time (s)');
ylabel('Amplitude');

% PLOT Time vs Smoothed Power
subplot(2,2,2);
plot(t, smoothed_power);
title('Power');
xlabel('Time (s)');
ylabel('Amplitude');

% PLOT Low frequency FFT signal
subplot(2,2,3);
plot(LF_f, LF_wavefft, LF_f(LF_index_max), LF_wavefft(LF_index_max), 'black s', 'MarkerSize', 5);
xlabel('Frequency (Hz)');
title('Frequency Response');

% PLOT Mid frequency FFT signal
subplot(2,2,4);
plot(MF_f, MF_wavefft, MF_f(MF_index_max), MF_wavefft(MF_index_max), 'black s', 'MarkerSize', 5);
xlabel('Frequency (Hz)');
title('Frequency Response');
