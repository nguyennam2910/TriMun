if 0
  cd /mas/vision/projects/AC/Notes-physiology/Elias/fulldata
  load BVPw.mat
  index

  cd /mas/vision/projects/AC/Notes-physiology/Elias/data
  load day1.mat
  chosenday=day1;

end

%signal = 4;

if (signal == 1)
  sign = 'EMGj_';
  range = 1:8;
elseif (signal == 2)
  sign = 'BVPw_';
  range = 9:16;
elseif (signal == 3)
  sign = 'GSR_';
  range = 17:24;
else
  sign = 'RESPw_';
  range = 25:32;
end 
  

for day=day

trans = sprintf('%c', 39);

clear i i2 i3;

%index

%imat =[37,43,45,49,51,60,62,65,67,68,69,71,72,75,76,79,81,82,84,85,86,87,89,90,91,93,94,99,100,105,107];


imat=[37,43,45,49,51,60,62,65,67,68,69,71,72,75,76,79,81,82,84,85,86,87,89,90,91,93,94,99,100,105,107];

clear j

j=range;

%eval(['load day' num2str(day) '.mat']);

eval(['chosenday=day' num2str(day) ';']);
d=[chosenday(:,j(1));chosenday(:,j(2));chosenday(:,j(3));chosenday(:,j(4));chosenday(:,j(5));chosenday(:,j(6));chosenday(:,j(7));chosenday(:,j(8))];

%d=chosenday(:,17);

ld=2001;
clear j
%6,8,12,17,24,21,26,27,28,29,30,31
%6,8,12,17,24,21,26,27,28,29,30,31
%jold=jold+1;
remember = remember;
for j=remember

  %eval(['i' num2str(imat(j)) ' = i' num2str(imat(j)) '- 100;']);

  eval(['dummy=[' sign num2str(imat(j)) '((i' num2str(imat(j)) '(2)-ld+1):i' num2str(imat(j)) '(2));' sign num2str(imat(j)) '((i' num2str(imat(j)) '(3)-ld+1):i' num2str(imat(j)) '(3));' sign num2str(imat(j)) '((i' num2str(imat(j)) '(4)-ld+1):i' num2str(imat(j)) '(4));' sign num2str(imat(j)) '((i' num2str(imat(j)) '(5)-ld+1):i' num2str(imat(j)) '(5));' sign num2str(imat(j)) '((i' num2str(imat(j)) '(6)-ld+1):i' num2str(imat(j)) '(6));' sign num2str(imat(j)) '((i' num2str(imat(j)) '(7)-ld+1):i' num2str(imat(j)) '(7));' sign num2str(imat(j)) '((i' num2str(imat(j)) '(8)-ld+1):i' num2str(imat(j)) '(8));' sign num2str(imat(j)) '((i' num2str(imat(j)) '(9)-ld+1):i' num2str(imat(j)) '(9))];']);
%  eval(['dummy=[' sign num2str(imat(j)) '((i' num2str(imat(j)) '(7)):i' num2str(imat(j)) '(8))];']);

%  i(j)=max(xcorr(d,dummy));
end
j
%plot(i)

%[a,b]=max(i);
%solution = [solution, imat(b)]

end
