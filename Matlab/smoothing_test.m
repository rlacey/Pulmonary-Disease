[wave,fs] = audioread('Sounds/vesicular6.mp3');

wave = wave(:,1);
t = (0:length(wave)-1)/fs;
CountWheezes( wave, t, fs )



% n = length(wave) - 1;
% f = 0:fs/n:fs;
% wavefft = abs(fft(wave));
% idx = find(f<800);
% 
% count = 0;
% 
% for i = 1:1665:length(t)-3310
%     lower = i;
%     higher = i+3310;
% %     t_i = t(lower:higher);
% %     wave_i = wave(lower:higher);
% %     n_i = length(wave_i) - 1;
% %     f_i = 0:fs/n_i:fs;
% %     wavefft_i = abs(fft(wave_i));
% %     if f_i(wavefft_i == max(wavefft_i)) > 10
% % %         plot(t_i, wave_i);
% %         plot(f_i, wavefft_i);
% %         break
% %     end
%     
%     t1 = t(lower:higher);
%     wave1 = wave(lower:higher);
% 
%     n1 = length(wave1) - 1;
%     f1 = 0:fs/n1:fs;
%     % f1 = f1 / (max(f1)/3000)
%     idx = find(f1>100 & f1<600);
%     f_tmp = f1(idx);
%     wavefft1 = abs(fft(wave1));
%     wavefft1_tmp = wavefft1(idx);
%     smoother = smooth(wavefft1_tmp, 30, 'loess');
%     if f_tmp(wavefft1_tmp == max(wavefft1_tmp)) > 200
%         count = count + 1;
%         min(t1)
%         max(t1)
%         threshold = 0.05 * max(smoother);
%         plot(f_tmp, wavefft1_tmp, f_tmp, smoother);
%         break
%     end
% end
% 
% count
% t1 = t(1:3000);
% wave1 = wave(1:3000);
% 
% n1 = length(wave1) - 1;
% f1 = 0:fs/n1:fs;
% % f1 = f1 / (max(f1)/3000)
% idx = find(f1>75 & f1<600);
% f_tmp = f1(idx);
% wavefft1 = abs(fft(wave1));
% wavefft1_tmp = wavefft1(idx);
% smoother = smooth(wavefft1_tmp, 30, 'loess');
% plot(f_tmp, smoother);