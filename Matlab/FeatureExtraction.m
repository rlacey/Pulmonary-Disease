close all;

% Extract sound data and sampling frequency
% Practical Clinical Skills
% [wave,fs] = audioread('~/Downloads/Sounds/WAV/practicalclinicalskills/crackles_coarse.wav');
% Aus Med
% [wave,fs] = audioread('~/Downloads/Sounds/Unconverted/ausmed/6-Monophonic Wheeze-Lung Sounds-Lung Sounds.m4a');
% iTunesU
% [wave,fs] = audioread('~/Downloads/Sounds/Unconverted/iTunesU - Spring DPTE 516 Medical Issues I - Lung/high_pitched_wheezing.mp3');
% Littman
% [wave,fs] = audioread('~/Downloads/Sounds/Unconverted/littmann/Normal vesicular.wav');
% Easy Austiculation
% [wave,fs] = audioread('~/Downloads/Sounds/Unconverted/easyascultation/high_wheeze.mp3');
[wave,fs] = audioread('Sounds/wheeze2.wav');


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
LF_indexes = find(f<200);
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



% % PLOT Time vs Amplitude
% subplot(2,2,1);
% plot(t, wave);
% title('Waveform');
% xlabel('Time (s)');
% ylabel('Amplitude');
% 
% % PLOT Time vs Smoothed Power
% subplot(2,2,2);
% plot(t, smoothed_power);
% title('Power');
% xlabel('Time (s)');
% ylabel('Amplitude');
% 
% % PLOT Low frequency FFT signal
% subplot(2,2,3);
% plot(LF_f, LF_wavefft, f(LF_index_max), LF_wavefft(LF_index_max), 'black s', 'MarkerSize', 5);
% xlabel('Frequency (Hz)');
% title('Frequency Response');
% 
% % PLOT Mid frequency FFT signal
% subplot(2,2,4);
% plot(MF_f, MF_wavefft, MF_f(MF_index_max), MF_wavefft(MF_index_max), 'black s', 'MarkerSize', 5);
% xlabel('Frequency (Hz)');
% title('Frequency Response');

ratio = MF_max/mean(MF_wavefft)
tmp1 = find(MF_wavefft * ratio < MF_max);
a = MF_wavefft;
a = MF_wavefft - mean(MF_wavefft);
tmp1 = find(a * ratio < max(a));
a(tmp1) = 0;

plot(MF_f, MF_wavefft, MF_f, a);

% ANALYTICS
avg_low = mean(LF_wavefft);
avg_med = mean(MF_wavefft);
% avg_high = mean(wavefft(high_frequency_indecies));
normf_avg_low = mean(LF_norm_wavefft);
normf_avg_med = mean(MF_norm_wavefft);
max_low = LF_f(LF_index_max);
max_med = MF_f(MF_index_max);
% max_high = max(wavefft(high_frequency_indecies));
[pks_low, locs_low] = findpeaks(LF_wavefft, 'NPEAKS', 3, 'SORTSTR', 'descend');
f_pk_low_1 = LF_f(locs_low(1));
f_pk_low_2 = LF_f(locs_low(2));
f_pk_low_3 = LF_f(locs_low(3));
[pks_med, locs_med] = findpeaks(MF_wavefft, 'NPEAKS', 3, 'SORTSTR', 'descend');
f_pk_med_1 = MF_f(locs_med(1));
f_pk_med_2 = MF_f(locs_med(2));
f_pk_med_3 = MF_f(locs_med(3));

% OUTPUT
data = [avg_low, avg_med, normf_avg_med, normf_avg_med, max_low, max_med, ...
    f_pk_low_1, f_pk_low_2, f_pk_low_3, f_pk_med_1, f_pk_med_2, f_pk_med_3];
dlmwrite('data.out', data, ',')
