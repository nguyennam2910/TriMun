%figure(1);plot(dummy)            
%figure(2);hold off;plot(d)       
%figure(3);hold off;plot(d);hold on;plot(dummy,'r')
day=6;
remember=remember+1;
for signal=1:1
  match2
  figure(1);hold off;subplot(4,1,signal);plot(d);hold on;plot(dummy,'r')
end
