function H0 = bandpassFilter(N, lowFrequency, highFrequency)
    middle = N/2;
    lowK = 60 * lowFrequency;
    highK = 60 * highFrequency;
    H0 = zeros(N,1);
    H0(middle-highK:middle-lowK) = 1;
    H0(middle+lowK:middle+highK) = 1;
end
