function [ outArr ] = insertionSort( arr )
%insertionSort A general implementation of Insertion Sort on nums
%   input: arr (an array of numbers)
%   output: outArr (the sorted array)

    for i=3:length(arr)
        key = arr(i);
        j = i - 1;
        while (j >= 0) && (arr(j) > key)
           arr(j + 1) = arr(j);
           j = j - 1;
        end
        arr(j + 1) = key;
    end
    outArr = arr;
end

