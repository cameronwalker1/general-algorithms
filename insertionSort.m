function [ outArr ] = insertionSort( arr )
%insertionSort A general implementation of Insertion Sort on nums
%   input: arr (an array of numbers)
%   output: outArr (the sorted array)

    for i=2:length(arr)
        j = i;
        while ((j > 0) && (arr(j - 1) > arr(j))
           swap = arr(j);
           arr(j) = arr(j - 1);
           arr(j - 1) = swap;
           j = j - 1;
        end
    end
    outArr = arr;
end
