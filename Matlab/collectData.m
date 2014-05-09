% Practical Clinical Skills
d1 = analyzeLungData('Sounds/vesicular1.wav');
d3 = analyzeLungData('Sounds/vesicular3.wav');
d4 = analyzeLungData('Sounds/vesicular4.wav');
d5 = analyzeLungData('Sounds/vesicular5.m4a');
d6 = analyzeLungData('Sounds/vesicular6.mp3');
d7 = analyzeLungData('Sounds/vesicular7.mp3');
d8 = analyzeLungData('Sounds/vesicular8.wav');
d9 = analyzeLungData('Sounds/vesicular9.mp3');
d10 = analyzeLungData('Sounds/wheeze1.wav');
% d1 = analyzeLungData('Sounds/wheeze3.wav');
d12 = analyzeLungData('Sounds/wheeze4.wav');
d13 = analyzeLungData('Sounds/wheeze5.m4a');
d14 = analyzeLungData('Sounds/wheeze6.mp3');
% d1 = analyzeLungData('Sounds/wheeze8.mp3');
% d1 = analyzeLungData('Sounds/wheeze9.wav');
d16 = analyzeLungData('Sounds/wheeze10.mp3');
% d1 = analyzeLungData('Sounds/wheeze11.mp3');
% d1 = analyzeLungData('Sounds/wheeze12.mp3');
% d1 = analyzeLungData('Sounds/wheeze13.mp3');



output = [d1;d3;d4;d5;d6;d7;d8;d9;d10;d12;d13;d14;d16];%;d17;d18;d19;d20;d21;d22;d23;d24;d25;d26;d27;d28;d29];
dlmwrite('data.out', output, ',');
done = 1