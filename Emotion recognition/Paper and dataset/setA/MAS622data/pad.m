function out = pad( in, x, y, offx, offy)

if nargin < 3
	y = 0;
end
if nargin < 4
	offx = 0;
end
if nargin < 5
	offy = 0;
end

out = zeros( x, y);
for i = 1:rows(in)
	for n = 1:cols( in)
		out(i+offx, n+offy) = in(i, n);
	end
end
