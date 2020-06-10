
%this runs a giant for loop for all emotions and sensors
for i=1:32,

	%find out which emotion
	if rem(i,8) == 1
		t1='No Emotion ';
	elseif rem(i,8)==2
		t1='Anger ';
	elseif rem(i,8)==3
		t1='Hate ';
	elseif rem(i,8)==4
		t1='Grief ';
	elseif rem(i,8)==5
		t1='P-Love ';
	elseif rem(i,8)==6
		t1='R-Love ';
	elseif rem(i,8)==7
		t1='Joy ';
	else
		t1='Reverence ';
	end;

	%find out which sensor
	if i/8 < 1.01
		t2='EMG(jaw)';
		ymin=-10;
		ymax=200;
	elseif i/8 <2.01
		t2='BVP';
		ymin=0;
		ymax=100;
	elseif i/8 < 3.01
		t2='GSR(palm)';
		ymin=0;
		ymax=15;	
	else
		t2='Respiration';
		ymin=35;
		ymax=70;
	end;

	%Creating the title as a string of the emotion and the sensor

	t=[t1 t2]

	xmin=0;
	xmax=2001;

	figure((2*i-1))
	subplot(5,2,1)
	plot(day1(:,i))
	axis([xmin xmax ymin ymax])
	ylabel('1');
	title(t)
	axis([xmin xmax ymin ymax])
	subplot(5,2,2)
	plot(day2(:,i))
	ylabel('2');
	axis([xmin xmax ymin ymax])
	subplot(5,2,3)
	plot(day3(:,i))
	axis([xmin xmax ymin ymax])
	ylabel('3');
	subplot(5,2,4)
	plot(day4(:,i))
	ylabel('4');
	axis([xmin xmax ymin ymax])	
	subplot(5,2,5)
	plot(day5(:,i))
	ylabel('5');
	axis([xmin xmax ymin ymax])
	subplot(5,2,6)
	plot(day6(:,i))
	ylabel('6');
	axis([xmin xmax ymin ymax])
	subplot(5,2,7)
	plot(day7(:,i))
	ylabel('7');
	axis([xmin xmax ymin ymax])
	subplot(5,2,8)
	plot(day8(:,i))
	ylabel('8');	
	axis([xmin xmax ymin ymax])
	subplot(5,2,9)
	plot(day9(:,i))
	ylabel('9');
	axis([xmin xmax ymin ymax])
	subplot(5,2,10)
	plot(day10(:,i))
	ylabel('10');
	axis([xmin xmax ymin ymax])

	figure(2*i)
	subplot(5,2,1)
	plot(day11(:,i))
	ylabel('11');
	title(t)
	axis([xmin xmax ymin ymax])
	subplot(5,2,2)
	plot(day12(:,i))
	ylabel('12');
	axis([xmin xmax ymin ymax])
	subplot(5,2,3)
	plot(day13(:,i))	
	ylabel('13');	
	axis([xmin xmax ymin ymax])
	subplot(5,2,4)
	plot(day14(:,i))
	ylabel('14');
	axis([xmin xmax ymin ymax])
	subplot(5,2,5)
	plot(day15(:,i))
	ylabel('15');
	axis([xmin xmax ymin ymax])
	subplot(5,2,6)
	plot(day16(:,i))	
	ylabel('16');
	axis([xmin xmax ymin ymax])
	subplot(5,2,7)
	plot(day17(:,i))
	axis([xmin xmax ymin ymax])
	ylabel('17');
	axis([xmin xmax ymin ymax])
	subplot(5,2,8)
	plot(day18(:,i))
	ylabel('18');
	axis([xmin xmax ymin ymax])
	subplot(5,2,9)	
	plot(day19(:,i))
	ylabel('19');
	axis([xmin xmax ymin ymax])
	subplot(5,2,10)
	plot(day20(:,i))
	axis([xmin xmax ymin ymax])
	ylabel('20');
end;















