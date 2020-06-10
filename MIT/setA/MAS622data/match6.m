if 0
  cd /mas/vision/projects/AC/Notes-physiology/Elias/fulldata
  load BVPw.mat
  index

  cd /mas/vision/projects/AC/Notes-physiology/Elias/data
  load day1.mat
  day1=day1;


clear i;

%j=17:24;
%d=[day1(:,j(1));day1(:,j(2));day1(:,j(3));day1(:,j(4));day1(:,j(5));day1(:,j(6));day1(:,j(7));day1(:,j(8))];
%break

d=day1(:,17);

m=1000;
n=5000;

i(1) = max(xcorr(d,BVPw_100(m:n)));
i(2) = max(xcorr(d,BVPw_60(m:n)));
i(3) = max(xcorr(d,BVPw_75(m:n)));
i(4) = max(xcorr(d,BVPw_88(m:n)));
i(5) = max(xcorr(d,BVPw_105(m:n)));
i(6) = max(xcorr(d,BVPw_62(m:n)));
i(7) = max(xcorr(d,BVPw_76(m:n)));
i(8) = max(xcorr(d,BVPw_89(m:n)));
i(9) = max(xcorr(d,BVPw_107(m:n)));
%i(10) = max(xcorr(d,BVPw_64(m:n)));
i(11) = max(xcorr(d,BVPw_79(m:n)));
i(12) = max(xcorr(d,BVPw_90(m:n)));
i(13) = max(xcorr(d,BVPw_37(m:n)));
i(14) = max(xcorr(d,BVPw_65(m:n)));
i(15) = max(xcorr(d,BVPw_81(m:n)));
i(16) = max(xcorr(d,BVPw_91(m:n)));
i(17) = max(xcorr(d,BVPw_43(m:n)));
i(18) = max(xcorr(d,BVPw_67(m:n)));
i(19) = max(xcorr(d,BVPw_82(m:n)));
i(20) = max(xcorr(d,BVPw_93(m:n)));
i(21) = max(xcorr(d,BVPw_45(m:n)));
i(22) = max(xcorr(d,BVPw_68(m:n)));
i(23) = max(xcorr(d,BVPw_84(m:n)));
i(24) = max(xcorr(d,BVPw_94(m:n)));
i(25) = max(xcorr(d,BVPw_49(m:n)));
i(26) = max(xcorr(d,BVPw_69(m:n)));
i(27) = max(xcorr(d,BVPw_85(m:n)));
i(28) = max(xcorr(d,BVPw_99(m:n)));
i(29) = max(xcorr(d,BVPw_51(m:n)));
i(30) = max(xcorr(d,BVPw_71(m:n)));
i(31) = max(xcorr(d,BVPw_86(m:n)));
%i(32) = max(xcorr(d,BVPw_58(m:n)));

i(33) = max(xcorr(d,BVPw_72(m:n)));
i(34) = max(xcorr(d,BVPw_87(m:n)));

end  

for day=day+1

trans = sprintf('%c', 39);

clear i i2 i3;

%index

%imat =[37,43,45,49,51,60,62,65,67,68,69,71,72,75,76,79,81,82,84,85,86,87,89,90,91,93,94,99,100,105,107];


imat=[37,43,45,49,51,60,62,65,67,68,69,71,72,75,76,79,81,82,84,85,86,87,89,90,91,93,94,99,100,105,107];

clear j

j=9:16;

%eval(['load day' num2str(day) '.mat']);

eval(['day1=day' num2str(day) ';']);
d=[day1(:,j(1));day1(:,j(2));day1(:,j(3));day1(:,j(4));day1(:,j(5));day1(:,j(6));day1(:,j(7));day1(:,j(8))];

%d=day1(:,17);

ld=2001;
eval([(i' num2str(imat(j)) '- 100');]);
clear j
%6,8,12,17,24,21,26,27,28,29,30,31
%6,8,12,17,24,21,26,27,28,29,30,31
%jold=jold+1;
remember = remember + 1 ;
for j=remember
  eval(['dummy=[BVPw_' num2str(imat(j)) '((i' num2str(imat(j)) '(2)-ld+1):i' num2str(imat(j)) '(2));BVPw_' num2str(imat(j)) '((i' num2str(imat(j)) '(3)-ld+1):i' num2str(imat(j)) '(3));BVPw_' num2str(imat(j)) '((i' num2str(imat(j)) '(4)-ld+1):i' num2str(imat(j)) '(4));BVPw_' num2str(imat(j)) '((i' num2str(imat(j)) '(5)-ld+1):i' num2str(imat(j)) '(5));BVPw_' num2str(imat(j)) '((i' num2str(imat(j)) '(6)-ld+1):i' num2str(imat(j)) '(6));BVPw_' num2str(imat(j)) '((i' num2str(imat(j)) '(7)-ld+1):i' num2str(imat(j)) '(7));BVPw_' num2str(imat(j)) '((i' num2str(imat(j)) '(8)-ld+1):i' num2str(imat(j)) '(8));BVPw_' num2str(imat(j)) '((i' num2str(imat(j)) '(9)-ld+1):i' num2str(imat(j)) '(9))];']);

  i(j)=max(xcorr(d,dummy));
end

%plot(i)

[a,b]=max(i);
solution = [solution, imat(b)]

end
