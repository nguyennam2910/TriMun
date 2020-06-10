function [i,m] = pmatch2( d, P)

if 0
cd /mas/disks/vismod/grad/fenn/EXPERIMENTS/SENTICS/DATA/MAT
load BVPr.mat
cd /mas/vision/projects/AC/Notes-physiology/Elias/data
load day1.mat
end

for i = 1:34
  m(i) = max( xcorr( d, P{i}));
end
[dd,i] = max( m);

%P = {BVPw_100(1:p) BVPw_58(1:p) BVPw_71(1:p) BVPw_85(1:p) BVPw_94(1:p) BVPw_105(1:p) BVPw_60(1:p) BVPw_72(1:p) BVPw_86(1:p) BVPw_99(1:p) BVPw_107(1:p) BVPw_62(1:p) BVPw_75(1:p) BVPw_87(1:p) BVPw_37 (1:p) BVPw_64(1:p) BVPw_76(1:p) BVPw_88(1:p) BVPw_43(1:p) BVPw_65(1:p) BVPw_79(1:p) BVPw_89(1:p) BVPw_45(1:p) BVPw_67(1:p) BVPw_81(1:p) BVPw_90(1:p) BVPw_49(1:p) BVPw_68(1:p) BVPw_82(1:p) BVPw_91(1:p) BVPw_51(1:p) BVPw_69(1:p) BVPw_84(1:p) BVPw_93(1:p)};

