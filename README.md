[![codecov](https://codecov.io/github/rusevradoslav/LeetCode75/graph/badge.svg?token=LG0zNafid5)](https://codecov.io/github/rusevradoslav/LeetCode75)
![CI](https://github.com/rusevradoslav/LeetCode75/actions/workflows/ci.yml/badge.svg)

# LeetCode 75: Java Solutions

This repository contains Java solutions for the LeetCode 75 study plan. Each solution includes documented code with time and space complexity analysis.

## Sections

- [Arrays & Strings](#arrays--strings)
- [Two Pointers](#two-pointers)
- [Sliding Window](#sliding-window)
- [Prefix Sum](#prefix-sum)
- [Hash Map / Set](#hash-map--set)
- [Stack](#stack)
- [Queue](#queue)
- [Linked List](#linked-list)
- [Binary Tree - DFS](#binary-tree---dfs)
- [Binary Tree - BFS](#binary-tree---bfs)
- [Binary Search Tree](#binary-search-tree)
- [Graphs - DFS](#graphs---dfs)
- [Graphs - BFS](#graphs---bfs)
- [Heap / Priority Queue](#heap--priority-queue)
- [Binary Search](#binary-search)
- [Backtracking](#backtracking)
- [DP - 1D](#dp---1d)
- [DP - Multidimensional](#dp---multidimensional)
- [Bit Manipulation](#bit-manipulation)
- [Trie](#trie)
- [Intervals](#intervals)
- [Monotonic Stack](#monotonic-stack)
---

## Arrays & Strings

### Quick Reference

| # | Problem | Difficulty | Time | Space | Pattern |
|---|---------|------------|------|-------|---------|
| 1 | [Merge Strings Alternately](#1-merge-strings-alternately) | Easy | O(n+m) | O(n+m) | [Two Pointers](#two-pointers-1) |
| 2 | [Greatest Common Divisor of Strings](#2-greatest-common-divisor-of-strings) | Easy | O(n+m) | O(n+m) | [Math (Euclidean Algorithm)](#math-euclidean-algorithm) |
| 3 | [Kids With the Greatest Number of Candies](#3-kids-with-the-greatest-number-of-candies) | Easy | O(n) | O(n) | [Two-Pass Scan](#two-pass-scan) |
| 4 | [Can Place Flowers](#4-can-place-flowers) | Easy | O(n) | O(1) | [Greedy](#greedy) |
| 5 | [Reverse Vowels of a String](#5-reverse-vowels-of-a-string) | Easy | O(n) | O(n) | [Two Pointers](#two-pointers-1) |
| 6 | [Reverse Words in a String](#6-reverse-words-in-a-string) | Medium | O(n) | O(n) | [String Manipulation](#string-manipulation) |
| 7 | [Product of Array Except Self](#7-product-of-array-except-self) | Medium | O(n) | O(1)* | [Prefix/Suffix](#prefixsuffix) |
| 8 | [Increasing Triplet Subsequence](#8-increasing-triplet-subsequence) | Medium | O(n) | O(1) | [Greedy](#greedy) |
| 9 | [String Compression](#9-string-compression) | Medium | O(n) | O(1) | [Read/Write Pointers](#readwrite-pointers) |
| 10 | [Reverse Integer Number](#10-reverse-integer-number) | Bonus | O(d) | O(1) | [Digit Manipulation](#digit-manipulation) |
| 11 | [Reverse Decimal Number](#11-reverse-decimal-number) | Bonus | O(d) | O(d) | [Digit Manipulation](#digit-manipulation) |

*\* O(1) excluding output array*

---

### 1. Merge Strings Alternately

**Approach:** Use two pointers, one for each string. Alternate appending characters while either pointer is in range.

**Time Complexity:** O(n + m) — each character visited exactly once.

**Space Complexity:** O(n + m) — StringBuilder holds the combined result.

**Pattern:** [Two Pointers](#two-pointers-1) — traverse two sequences simultaneously using separate pointers.

**Key Insight:** No length comparison needed — the OR condition and two separate if statements naturally handle strings of any length.

**Code:**
```java
int p1 = 0, p2 = 0;
StringBuilder result = new StringBuilder();

while (p1 < word1.length() || p2 < word2.length()) {
    if (p1 < word1.length()) {
        result.append(word1.charAt(p1++));
    }
    if (p2 < word2.length()) {
        result.append(word2.charAt(p2++));
    }
}

return result.toString();
```

---

### 2. Greatest Common Divisor of Strings

**Approach:** First check if both strings share the same repeating pattern by comparing concatenations. If they do, the GCD of their lengths gives the length of the answer.

**Time Complexity:** O(n + m) — string concatenation and comparison.

**Space Complexity:** O(n + m) — concatenated strings.

**Pattern:** [Math (Euclidean Algorithm)](#math-euclidean-algorithm) — repeatedly divide to find the greatest common divisor.

**Key Insight:** If `str1 + str2` equals `str2 + str1`, they share a common pattern. The GCD of lengths tells us the pattern length.

**Code:**
```java
if (!(str1 + str2).equals(str2 + str1)) {
    return "";
}

int gcdLength = gcd(str1.length(), str2.length());
return str1.substring(0, gcdLength);

// Euclidean algorithm
int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
}
```

---

### 3. Kids With the Greatest Number of Candies

**Approach:** First pass finds the maximum candies. Second pass checks if each kid's candies plus extra meets or exceeds the maximum.

**Time Complexity:** O(n) — two passes through the array.

**Space Complexity:** O(n) — result list stores n booleans.

**Pattern:** [Two-Pass Scan](#two-pass-scan) — first pass gathers global info, second pass uses it.

**Key Insight:** You need a global value (max) before you can compare each element against it.

**Code:**
```java
int maxCandies = 0;
for (int candy : candies) {
maxCandies = Math.max(maxCandies, candy);
}

List<Boolean> result = new ArrayList<>();
for (int candy : candies) {
        result.add(candy + extraCandies >= maxCandies);
}

        return result;
```

---

### 4. Can Place Flowers

**Approach:** Greedy — plant a flower whenever the current plot and both neighbors are empty. Update the array immediately to prevent adjacent placements.

**Time Complexity:** O(n) — single pass through the array.

**Space Complexity:** O(1) — modify array in place.

**Pattern:** [Greedy](#greedy) — make the locally optimal choice at each step.

**Key Insight:** Planting at the earliest valid position never blocks a better solution. Skipping a valid spot gains nothing.

**Code:**
```java
for (int i = 0; i < flowerbed.length; i++) {
    boolean left = (i == 0) || (flowerbed[i - 1] == 0);
    boolean right = (i == flowerbed.length - 1) || (flowerbed[i + 1] == 0);

    if (left && right && flowerbed[i] == 0) {
        flowerbed[i] = 1;
        n--;
    }
}

return n <= 0;
```

---

### 5. Reverse Vowels of a String

**Approach:** Two pointers from opposite ends. Move inward, swapping only when both pointers point to vowels.

**Time Complexity:** O(n) — each character visited at most once.

**Space Complexity:** O(n) — char array to allow swapping.

**Pattern:** [Two Pointers](#two-pointers-1) — traverse from opposite ends, swapping when conditions are met.

**Key Insight:** Use else-if to ensure only one action per iteration — either swap, move left, or move right.

**Code:**
```java
char[] arr = s.toCharArray();
int left = 0, right = arr.length - 1;

while (left < right) {
    if (isVowel(arr[left]) && isVowel(arr[right])) {
        char temp = arr[left];
        arr[left++] = arr[right];
        arr[right--] = temp;
    } else if (!isVowel(arr[left])) {
        left++;
    } else {
        right--;
    }
}

return new String(arr);
```

---

### 6. Reverse Words in a String

**Approach:** Trim whitespace, split by one or more spaces using regex, then iterate backwards to build the result.

**Time Complexity:** O(n) — each character processed a constant number of times.

**Space Complexity:** O(n) — words array and StringBuilder.

**Pattern:** [String Manipulation](#string-manipulation) — split, transform, and rebuild the string.

**Key Insight:** The regex `\\s+` handles multiple spaces between words automatically.

**Code:**
```java
String[] words = s.trim().split("\\s+");
StringBuilder result = new StringBuilder();

for (int i = words.length - 1; i >= 0; i--) {
    result.append(words[i]);
    if (i > 0) {
        result.append(" ");
    }
}

return result.toString();
```

---

### 7. Product of Array Except Self

**Approach:** Build prefix products (product of all elements before index i) and suffix products (product of all elements after index i). Multiply them for the final result.

**Time Complexity:** O(n) — two or three passes through the array.

**Space Complexity:** O(n) standard version, O(1) optimized version (excluding output).

**Pattern:** [Prefix/Suffix](#prefixsuffix) — precompute cumulative values from both directions.

**Key Insight:** Start prefix and suffix with 1 (multiplicative identity) because "nothing to multiply" should not affect the result. Using 0 would break everything.

**Pseudocode (standard):**
```
prefixProducts[0] = 1
for i = 1 to n-1:
    prefixProducts[i] = prefixProducts[i-1] * nums[i-1]

suffixProducts[n-1] = 1
for i = n-2 down to 0:
    suffixProducts[i] = suffixProducts[i+1] * nums[i+1]

for i = 0 to n-1:
    result[i] = prefixProducts[i] * suffixProducts[i]
```

**Pseudocode (space optimized):**
```
result[0] = 1
for i = 1 to n-1:
    result[i] = result[i-1] * nums[i-1]   // prefix in result

suffix = 1
for i = n-1 down to 0:
    result[i] = result[i] * suffix
    suffix = suffix * nums[i]
```

---

### 8. Increasing Triplet Subsequence

**Approach:** Track two candidates — the smallest and second smallest values seen so far. If any value exceeds both, a triplet exists.

**Time Complexity:** O(n) — single pass through the array.

**Space Complexity:** O(1) — only two variables.

**Pattern:** [Greedy](#greedy) — track optimal candidates while scanning.

**Key Insight:** We don't need to track indices — just knowing that smaller values exist before is enough.

**Code:**
```java
int first = Integer.MAX_VALUE;
int second = Integer.MAX_VALUE;

for (int num : nums) {
    if (num <= first) {
        first = num;
    } else if (num <= second) {
        second = num;
    } else {
        return true;
    }
}

return false;
```

---

### 9. String Compression

**Approach:** Use read/write pointers. Read pointer counts consecutive characters, write pointer overwrites the array with the character and its count.

**Time Complexity:** O(n) — each character read once.

**Space Complexity:** O(1) — compression happens in place.

**Pattern:** [Read/Write Pointers](#readwrite-pointers) — read ahead while writing in place behind.

**Key Insight:** Compressed output is always ≤ original length, so write pointer never overtakes read pointer.

**Code:**
```java
int write = 0, read = 0;

while (read < chars.length) {
    char currentChar = chars[read];
    int count = 0;

    while (read < chars.length && chars[read] == currentChar) {
        read++;
        count++;
    }

    chars[write++] = currentChar;
    if (count > 1) {
        for (char c : String.valueOf(count).toCharArray()) {
            chars[write++] = c;
        }
    }
}

return write;
```

---

### 10. Reverse Integer Number

**Approach:** Extract digits using modulo, build reversed number by multiplying by 10 and adding each digit.

**Time Complexity:** O(d) — where d is the number of digits (log₁₀n).

**Space Complexity:** O(1) — only a single variable.

**Pattern:** [Digit Manipulation](#digit-manipulation) — extract and rebuild digits using modulo and division.

**Key Insight:** Check for overflow before multiplying, not after. Java's `%` preserves sign, so negative numbers work automatically.

**Code:**
```java
int reversed = 0;

while (number != 0) {
    if (willOverflow(reversed)) {
        return 0;
    }
    reversed = reversed * 10 + number % 10;
    number /= 10;
}

return reversed;

boolean willOverflow(int num) {
    return num > Integer.MAX_VALUE / 10 || num < Integer.MIN_VALUE / 10;
}
```

---

### 11. Reverse Decimal Number

**Approach:** Split the number into integer and fractional parts. Reverse each part separately, then swap their positions around the decimal point.

**Time Complexity:** O(d) — where d is total number of digits.

**Space Complexity:** O(d) — string operations for splitting.

**Pattern:** [Digit Manipulation](#digit-manipulation) — extract, reverse, and reposition decimal parts.

**Key Insight:** Reversed integer part becomes the new fractional part (scaled by 10^length), reversed fractional part becomes the new integer part.

**Pseudocode:**
```
split number by "." into integerPart and fractionalPart
store the sign, work with absolute value

reversedFractional = reverse(fractionalPart)  // becomes new integer
reversedInteger = reverse(integerPart)        // becomes new fractional

newFractional = reversedInteger / 10^(length of integerPart)
result = (reversedFractional + newFractional) * sign

return result
```

---

## Two Pointers

### Quick Reference

| # | Problem | Difficulty | Time | Space | Pattern |
|---|---------|------------|------|-------|---------|
| 1 | [Move Zeroes](#1-move-zeroes) | Easy | O(n) | O(1) | [Slow/Fast Pointers](#slowfast-pointers) |
| 2 | [Is Subsequence](#2-is-subsequence) | Easy | O(n) | O(1) | [Slow/Fast Pointers](#slowfast-pointers) |
| 3 | [Container With Most Water](#3-container-with-most-water) | Medium | O(n) | O(1) | [Two Pointers (Opposite Ends)](#two-pointers-1) |
| 4 | [Max Number of K-Sum Pairs](#4-max-number-of-k-sum-pairs) | Medium | O(n log n) | O(1) | [Two Pointers (Opposite Ends)](#two-pointers-1) |

---

### 1. Move Zeroes

**Approach:** Use slow/fast two-pointer technique. Slow pointer tracks where the next non-zero element should go. Fast pointer scans for non-zero elements to swap.

**Time Complexity:** O(n) — each element visited exactly once.

**Space Complexity:** O(1) — in-place swapping.

**Pattern:** [Slow/Fast Pointers](#slowfast-pointers) — slow marks placement position, fast scans ahead.

**Key Insight:** When fast finds a non-zero, swap it with slow's position. This naturally pushes zeros to the end while maintaining order.

**Code:**
```java
int slow = 0, fast = 0;

while (fast < nums.length) {
    if (nums[fast] != 0) {
        int temp = nums[slow];
        nums[slow] = nums[fast];
        nums[fast] = temp;
        slow++;
    }
    fast++;
}
```

---

### 2. Is Subsequence

**Approach:** Use two pointers — one for the subsequence string, one for the source string. When characters match, advance both; otherwise advance only the source pointer.

**Time Complexity:** O(n) — where n is the length of the source string t.

**Space Complexity:** O(1) — only pointer variables.

**Pattern:** [Slow/Fast Pointers](#slowfast-pointers) — subsequence pointer only moves on match, source pointer always moves.

**Key Insight:** We don't need to track matches separately — if the subsequence pointer reaches the end, all characters were found in order.

**Code:**
```java
int sIndex = 0, tIndex = 0;

while (sIndex < s.length() && tIndex < t.length()) {
    if (s.charAt(sIndex) == t.charAt(tIndex)) {
        sIndex++;
    }
    tIndex++;
}

return sIndex == s.length();
```

---

### 3. Container With Most Water

**Approach:** Start with pointers at both ends (maximum width). Calculate area, then move the pointer pointing to the shorter line inward — keeping the shorter line can never increase area.

**Time Complexity:** O(n) — each element visited at most once.

**Space Complexity:** O(1) — only pointer and max variables.

**Pattern:** [Two Pointers (Opposite Ends)](#two-pointers-1) — start wide, narrow based on which side limits the area.

**Key Insight:** Water is limited by the shorter line. Moving the taller line inward can only decrease width with no chance of increasing height. Moving the shorter line might find a taller one.

**Code:**
```java
int left = 0, right = height.length - 1;
int maxArea = 0;

while (left < right) {
    int width = right - left;
    int minHeight = Math.min(height[left], height[right]);
    maxArea = Math.max(maxArea, width * minHeight);

    if (height[left] < height[right]) {
        left++;
    } else {
        right--;
    }
}

return maxArea;
```

---

### 4. Max Number of K-Sum Pairs

**Approach:** Sort the array first. Use two pointers from opposite ends. If sum equals k, count the pair and move both pointers. If sum is too small, move left pointer right. If sum is too large, move right pointer left.

**Time Complexity:** O(n log n) — dominated by sorting.

**Space Complexity:** O(1) — or O(n) depending on sort implementation.

**Pattern:** [Two Pointers (Opposite Ends)](#two-pointers-1) — after sorting, adjust pointers based on sum comparison.

**Key Insight:** Sorting enables the two-pointer approach. Too small? Need bigger numbers (move left). Too big? Need smaller numbers (move right).

**Code:**
```java
Arrays.sort(nums);
int count = 0, left = 0, right = nums.length - 1;

while (left < right) {
    int sum = nums[left] + nums[right];
    if (sum == k) {
        count++;
        left++;
        right--;
    } else if (sum < k) {
        left++;
    } else {
        right--;
    }
}

return count;
```

---

## Sliding Window

### Quick Reference

| # | Problem | Difficulty | Time | Space | Pattern |
|---|---------|------------|------|-------|---------|
| 1 | [Maximum Average Subarray I](#1-maximum-average-subarray-i) | Easy | O(n) | O(1) | [Fixed-Size Sliding Window](#fixed-size-sliding-window) |
| 2 | [Maximum Number of Vowels in a Substring of Given Length](#2-maximum-number-of-vowels-in-a-substring-of-given-length) | Medium | O(n) | O(1) | [Fixed-Size Sliding Window](#fixed-size-sliding-window) |
| 3 | [Max Consecutive Ones III](#3-max-consecutive-ones-iii) | Medium | O(n) | O(1) | [Variable-Size Sliding Window](#variable-size-sliding-window) |
| 4 | [Longest Subarray of 1's After Deleting One Element](#4-longest-subarray-of-1s-after-deleting-one-element) | Medium | O(n) | O(1) | [Variable-Size Sliding Window](#variable-size-sliding-window) |

---

### 1. Maximum Average Subarray I

**Approach:** Calculate the sum of the first k elements. Then slide the window: add the entering element, subtract the leaving element. Track the maximum sum.

**Time Complexity:** O(n) — each element visited at most twice.

**Space Complexity:** O(1) — only a few variables.

**Pattern:** [Fixed-Size Sliding Window](#fixed-size-sliding-window) — window size stays constant at k.

**Key Insight:** Instead of recalculating sum for each window (O(k) per window), reuse the previous sum: `new_sum = old_sum - leaving + entering`. This reduces each window calculation to O(1).

**Code:**
```java
double windowSum = 0;
for (int i = 0; i < k; i++) {
    windowSum += nums[i];
}

double maxSum = windowSum;
for (int i = k; i < nums.length; i++) {
    windowSum += nums[i] - nums[i - k];
    maxSum = Math.max(maxSum, windowSum);
}

return maxSum / k;
```

---

### 2. Maximum Number of Vowels in a Substring of Given Length

**Approach:** Count vowels in the first window. Then slide: increment count if entering char is a vowel, decrement if leaving char is a vowel. Track maximum.

**Time Complexity:** O(n) — each character visited at most twice.

**Space Complexity:** O(1) — only counter variables.

**Pattern:** [Fixed-Size Sliding Window](#fixed-size-sliding-window) — window size stays constant at k.

**Key Insight:** Same principle as Maximum Average — maintain a running count instead of recounting vowels for each window.

**Code:**
```java
int vowelCount = 0;
for (int i = 0; i < k; i++) {
    if (isVowel(s.charAt(i))) vowelCount++;
}

int maxCount = vowelCount;
for (int i = k; i < s.length(); i++) {
    if (isVowel(s.charAt(i))) vowelCount++;
    if (isVowel(s.charAt(i - k))) vowelCount--;
    maxCount = Math.max(maxCount, vowelCount);
}

return maxCount;
```

---

### 3. Max Consecutive Ones III

**Approach:** Reframe as "find the longest subarray with at most k zeros". Expand window with right pointer. When zero count exceeds k, shrink from left until valid.

**Time Complexity:** O(n) — each element visited at most twice (once by right, once by left).

**Space Complexity:** O(1) — only a few variables.

**Pattern:** [Variable-Size Sliding Window](#variable-size-sliding-window) — window expands and shrinks based on constraint.

**Key Insight:** We don't actually flip zeros — we just find a window where zero count is ≤ k. The window length is the answer.

**Code:**
```java
int left = 0, maxLength = 0, zeroCount = 0;

for (int right = 0; right < nums.length; right++) {
    if (nums[right] == 0) {
        zeroCount++;
    }

    while (zeroCount > k) {
        if (nums[left] == 0) {
            zeroCount--;
        }
        left++;
    }

    maxLength = Math.max(maxLength, right - left + 1);
}

return maxLength;
```

---

### 4. Longest Subarray of 1's After Deleting One Element

**Approach:** Reframe as "find the longest subarray with at most 1 zero". Since we must delete one element, the result is window length minus 1 (use `right - left` instead of `right - left + 1`).

**Time Complexity:** O(n) — each element visited at most twice.

**Space Complexity:** O(1) — only a few variables.

**Pattern:** [Variable-Size Sliding Window](#variable-size-sliding-window) — same as Max Consecutive Ones but with k=1 and mandatory deletion.

**Key Insight:** This is Max Consecutive Ones III with k=1, but we must delete exactly one element. Using `right - left` instead of `right - left + 1` accounts for the mandatory deletion.

**Code:**
```java
int left = 0, maxLength = 0, zeroCount = 0;

for (int right = 0; right < nums.length; right++) {
    if (nums[right] == 0) {
        zeroCount++;
    }

    if (zeroCount <= 1) {
        maxLength = Math.max(maxLength, right - left);
    } else {
        if (nums[left] == 0) {
            zeroCount--;
        }
        left++;
    }
}

return maxLength;
```

---

## Prefix Sum

### Quick Reference

| # | Problem | Difficulty | Time | Space | Pattern |
|---|---------|------------|------|-------|---------|
| 1 | [Find the Highest Altitude](#1-find-the-highest-altitude) | Easy | O(n) | O(1) | [Running Prefix Sum](#running-prefix-sum) |
| 2 | [Find Pivot Index](#2-find-pivot-index) | Easy | O(n) | O(1) | [Running Prefix Sum](#running-prefix-sum) |

---

### 1. Find the Highest Altitude

**Approach:** Maintain a running prefix sum representing the current altitude. Track the maximum altitude seen. Compare with starting altitude 0.

**Time Complexity:** O(n) — single pass through the array.

**Space Complexity:** O(1) for optimized version, O(n) for explicit array version.

**Pattern:** [Running Prefix Sum](#running-prefix-sum) — accumulate values while tracking a result.

**Key Insight:** We don't need to store all altitudes — just track the running sum and maximum. Don't forget to compare with starting altitude 0.

**Code:**
```java
int maxAltitude = 0;
int currentAltitude = 0;

for (int gain : gains) {
    currentAltitude += gain;
    maxAltitude = Math.max(maxAltitude, currentAltitude);
}

return maxAltitude;
```

---

### 2. Find Pivot Index

**Approach:** Compute total sum first. Then iterate while maintaining left sum. Right sum = totalSum - leftSum - nums[i]. Return index when they're equal.

**Time Complexity:** O(n) — two passes through the array.

**Space Complexity:** O(1) for optimized version, O(n) for dual array version.

**Pattern:** [Running Prefix Sum](#running-prefix-sum) — use total sum to derive right sum from left sum.

**Key Insight:** Instead of building two arrays, use the formula: `rightSum = totalSum - leftSum - nums[i]`. Update leftSum *after* the comparison.

**Code:**
```java
int totalSum = 0;
for (int num : nums) {
    totalSum += num;
}

int leftSum = 0;
for (int i = 0; i < nums.length; i++) {
    if (leftSum == totalSum - leftSum - nums[i]) {
        return i;
    }
    leftSum += nums[i];
}

return -1;
```

---

## Hash Map / Set

### Quick Reference

| # | Problem | Difficulty | Time | Space | Pattern |
|---|---------|------------|------|-------|---------|
| 1 | [Find the Difference of Two Arrays](#1-find-the-difference-of-two-arrays) | Easy | O(n+m) | O(n+m) | [Set Difference](#set-difference) |
| 2 | [Unique Number of Occurrences](#2-unique-number-of-occurrences) | Easy | O(n) | O(k) | [Frequency Map](#frequency-map) |
| 3 | [Determine If Two Strings Are Close](#3-determine-if-two-strings-are-close) | Medium | O(n + k log k) | O(k) | [Frequency Distribution](#frequency-distribution) |
| 4 | [Determine If Two Strings Are Close (Optimized)](#4-determine-if-two-strings-are-close-optimized) | Medium | O(n) | O(1) | [Frequency Distribution](#frequency-distribution) |
| 5 | [Equal Row and Column Pairs](#5-equal-row-and-column-pairs) | Medium | O(n²) | O(n²) | [Hashing Rows/Columns](#hashing-rowscolumns) |

---

### 1. Find the Difference of Two Arrays

**Approach:** Convert both arrays to sets to remove duplicates. Remove elements found in the opposite set to get each side's unique values.

**Time Complexity:** O(n + m) — building sets and removing elements is linear with hash lookups.

**Space Complexity:** O(n + m) — stores both sets and result lists.

**Pattern:** [Set Difference](#set-difference) — compute elements present in one set but not the other.

**Key Insight:** Once in sets, `removeAll` does exactly the difference we need.

**Code:**
```java
Set<Integer> first = new HashSet<>();
for (int num : nums1) {
    first.add(num);
}

Set<Integer> second = new HashSet<>();
for (int num : nums2) {
    second.add(num);
}

List<Integer> onlyFirst = new ArrayList<>(first);
onlyFirst.removeAll(second);

List<Integer> onlySecond = new ArrayList<>(second);
onlySecond.removeAll(first);

return List.of(onlyFirst, onlySecond);
```

---

### 2. Unique Number of Occurrences

**Approach:** Count occurrences with a map. If the number of distinct counts equals the number of keys, all counts are unique.

**Time Complexity:** O(n) — single pass for counts plus distinct check on the map values.

**Space Complexity:** O(k) — k is the number of distinct values.

**Pattern:** [Frequency Map](#frequency-map) — count how many times each value appears.

**Key Insight:** Uniqueness of counts is equivalent to “map size == distinct count values”.

**Code:**
```java
Map<Integer, Integer> counts = new HashMap<>();
for (int num : arr) {
    counts.put(num, counts.getOrDefault(num, 0) + 1);
}

long distinctCounts = counts.values().stream().distinct().count();
return counts.size() == distinctCounts;
```

---

### 3. Determine If Two Strings Are Close

**Approach:** Build frequency maps for both words. They are close if they use the same character set and their sorted frequency lists are identical.

**Time Complexity:** O(n + k log k) — counting is O(n), sorting frequencies is O(k log k).

**Space Complexity:** O(k) — for the frequency maps and lists.

**Pattern:** [Frequency Distribution](#frequency-distribution) — compare multiset of counts after validating the character set.

**Key Insight:** Operation 1 permutes positions, operation 2 swaps counts between characters — so only the character set and count multiset matter.

**Code:**
```java
if (word1.length() != word2.length()) {
    return false;
}

Map<Character, Integer> map1 = new HashMap<>();
for (char ch : word1.toCharArray()) {
    map1.put(ch, map1.getOrDefault(ch, 0) + 1);
}

Map<Character, Integer> map2 = new HashMap<>();
for (char ch : word2.toCharArray()) {
    map2.put(ch, map2.getOrDefault(ch, 0) + 1);
}

if (!map1.keySet().equals(map2.keySet())) {
    return false;
}

List<Integer> f1 = new ArrayList<>(map1.values());
List<Integer> f2 = new ArrayList<>(map2.values());
Collections.sort(f1);
Collections.sort(f2);

return f1.equals(f2);
```

---

### 4. Determine If Two Strings Are Close (Optimized)

**Approach:** Use fixed-size arrays of length 26 to count character frequencies. Verify the same character set and compare sorted arrays.

**Time Complexity:** O(n) — counting is linear; sorting 26-length arrays is O(1).

**Space Complexity:** O(1) — constant-size arrays.

**Pattern:** [Frequency Distribution](#frequency-distribution) — same logic as above with fixed alphabet.

**Key Insight:** For lowercase letters, arrays replace maps and shrink both time constants and memory.

**Code:**
```java
if (word1.length() != word2.length()) {
    return false;
}

int[] freq1 = new int[26];
int[] freq2 = new int[26];

for (char ch : word1.toCharArray()) {
    freq1[ch - 'a']++;
}
for (char ch : word2.toCharArray()) {
    freq2[ch - 'a']++;
}

for (int i = 0; i < 26; i++) {
    if ((freq1[i] == 0) != (freq2[i] == 0)) {
        return false;
    }
}

Arrays.sort(freq1);
Arrays.sort(freq2);
return Arrays.equals(freq1, freq2);
```

---

### 5. Equal Row and Column Pairs

**Approach:** Convert each row into a `List<Integer>` and count how many times it appears. Then build each column as a list and look it up in the map, adding its frequency.

**Time Complexity:** O(n²) — build all row and column representations across n² cells.

**Space Complexity:** O(n²) — store n row lists of length n in the map.

**Pattern:** [Hashing Rows/Columns](#hashing-rowscolumns) — normalize rows/columns into hashable keys and count.

**Key Insight:** A column can match multiple identical rows; storing row frequencies lets each column contribute the right count.

**Code:**
```java
Map<List<Integer>, Integer> counts = new HashMap<>();

for (int[] row : grid) {
    List<Integer> key = Arrays.stream(row).boxed().toList();
    counts.put(key, counts.getOrDefault(key, 0) + 1);
}

int result = 0;
for (int i = 0; i < grid.length; i++) {
    int[] col = new int[grid.length];
    for (int j = 0; j < grid.length; j++) {
        col[j] = grid[j][i];
    }
    List<Integer> key = Arrays.stream(col).boxed().toList();
    result += counts.getOrDefault(key, 0);
}

return result;
```

---

## Stack

### Quick Reference

| # | Problem | Difficulty | Time | Space | Pattern |
|---|---------|------------|------|-------|---------|
| 1 | [Removing Stars From a String](#1-removing-stars-from-a-string) | Medium | O(n) | O(n) | [Stack (LIFO)](#stack-lifo) |
| 2 | [Asteroid Collision](#2-asteroid-collision) | Medium | O(n) | O(n) | [Stack (Collision Resolution)](#stack-lifo) |
| 3 | [Decode String](#3-decode-string) | Medium | O(n) | O(n) | [Stack (Nested Encoding)](#stack-lifo) |

---

### 1. Removing Stars From a String

**Approach:** Use a stack. Push letters; when a `*` appears, pop the most recent letter. Rebuild the string in left-to-right order.

**Time Complexity:** O(n) — each character is pushed and popped at most once.

**Space Complexity:** O(n) — stack stores remaining characters.

**Pattern:** [Stack (LIFO)](#stack-lifo) — last inserted character is removed first.

**Key Insight:** Each star only removes the closest non-star character to its left, which is exactly the most recent one.

**Code:**
```java
Deque<Character> stack = new ArrayDeque<>();
for (char ch : s.toCharArray()) {
    if (ch == '*') {
        stack.pop();
    } else {
        stack.push(ch);
    }
}

StringBuilder result = new StringBuilder();
while (!stack.isEmpty()) {
    result.append(stack.removeLast());
}
return result.toString();
```

---

### 2. Asteroid Collision

**Approach:** Keep a stack of surviving asteroids. When a left-moving asteroid meets a right-moving top, resolve collisions by popping smaller ones; equal sizes remove both.

**Time Complexity:** O(n) — each asteroid is pushed and popped at most once.

**Space Complexity:** O(n) — stack of survivors.

**Pattern:** [Stack (Collision Resolution)](#stack-lifo) — only the latest right-moving asteroid can collide with the current left-moving one.

**Key Insight:** Collisions only occur at the boundary between a right-moving asteroid already on the stack and a left-moving incoming asteroid.

**Code:**
```java
Deque<Integer> stack = new ArrayDeque<>();
for (int a : asteroids) {
    boolean alive = true;
    while (alive && a < 0 && !stack.isEmpty() && stack.peekLast() > 0) {
        int top = stack.peekLast();
        if (top < -a) {
            stack.pollLast();
            continue;
        }
        if (top == -a) {
            stack.pollLast();
        }
        alive = false;
    }
    if (alive) {
        stack.add(a);
    }
}
```

---

### 3. Decode String

**Approach:** Use two stacks: one for repeat counts and one for partial strings. On `[`, push current state; on `]`, pop and expand the substring.

**Time Complexity:** O(n) — each character is processed once in the stack solution.

**Space Complexity:** O(n) — stacks hold counts and partial strings.

**Pattern:** [Stack (Nested Encoding)](#stack-lifo) — decode innermost brackets first.

**Key Insight:** Bracket nesting naturally follows a last-in-first-out order.

**Code:**
```java
Deque<Integer> countStack = new ArrayDeque<>();
Deque<StringBuilder> stringStack = new ArrayDeque<>();
StringBuilder current = new StringBuilder();
int count = 0;

for (char c : s.toCharArray()) {
    if (Character.isDigit(c)) {
        count = count * 10 + (c - '0');
    } else if (c == '[') {
        countStack.push(count);
        stringStack.push(current);
        count = 0;
        current = new StringBuilder();
    } else if (c == ']') {
        int repeat = countStack.pop();
        StringBuilder prev = stringStack.pop();
        prev.append(current.toString().repeat(repeat));
        current = prev;
    } else {
        current.append(c);
    }
}
return current.toString();
```

---

## Queue

### Quick Reference

| # | Problem | Difficulty | Time | Space | Pattern |
|---|---------|------------|------|-------|---------|
| 1 | [Number of Recent Calls](#1-number-of-recent-calls) | Easy | O(1)* | O(n) | [Queue (FIFO)](#queue-fifo) |
| 2 | [Dota2 Senate](#2-dota2-senate) | Medium | O(n) | O(n) | [Two Queues with Indices](#queue-fifo) |

*\* O(1) amortized — each timestamp is enqueued and dequeued at most once*

---

### 1. Number of Recent Calls

**Approach:** Maintain a FIFO queue of timestamps. On each ping, enqueue the new timestamp, then dequeue all timestamps older than `t - 3000`. The queue size is the answer.

**Time Complexity:** O(1) amortized — each timestamp is enqueued and dequeued at most once.

**Space Complexity:** O(n) — where n is the number of pings in the current 3000ms window.

**Pattern:** [Queue (FIFO)](#queue-fifo) — expired timestamps are always at the front, so a simple while loop removes them.

**Key Insight:** Since `t` is strictly increasing, the queue is always sorted. Expired elements are always at the front, making this a natural fit for a queue rather than a stack or sorted structure.

**Code:**
```java
private final Deque<Integer> queue = new ArrayDeque<>();

public int ping(int t) {
    queue.offer(t);
    while (!queue.isEmpty() && queue.peek() < t - 3000) {
        queue.poll();
    }
    return queue.size();
}
```

---

### 2. Dota2 Senate

**Approach:** Use two queues storing senator indices — one for Radiant, one for Dire. Compare the fronts: the lower index acts first and bans the other. The winner re-enters their queue with `index + n` to represent the next round. Repeat until one queue is empty.

**Time Complexity:** O(n) — each senator is processed at most twice. Each comparison is O(1).

**Space Complexity:** O(n) — both queues together hold all n senators.

**Pattern:** [Queue (FIFO)](#queue-fifo) — two queues simulate the circular left-to-right voting order.

**Key Insight:** The `+ n` offset elegantly handles circular rounds. A senator at index 0 who survives round 1 becomes index `n`, correctly placing them after all round-1 opponents. No need to re-scan the string.

**Code:**
```java
ArrayDeque<Integer> radiantQueue = new ArrayDeque<>();
ArrayDeque<Integer> direQueue = new ArrayDeque<>();

char[] chars = senate.toCharArray();
int n = chars.length;
for (int i = 0; i < n; i++) {
    if (chars[i] == 'R') radiantQueue.offer(i);
    else direQueue.offer(i);
}

while (!radiantQueue.isEmpty() && !direQueue.isEmpty()) {
    int r = radiantQueue.poll();
    int d = direQueue.poll();
    if (r < d) radiantQueue.offer(r + n);
    else direQueue.offer(d + n);
}

return radiantQueue.isEmpty() ? "Dire" : "Radiant";
```

---

## Linked List

### Quick Reference

| # | Problem | Difficulty | Time | Space | Pattern                                             |
|---|---------|------------|------|-------|-----------------------------------------------------|
| 1 | [Delete the Middle Node of a Linked List](#1-delete-the-middle-node-of-a-linked-list) | Medium | O(n) | O(1) | [Two-Pass Count and Walk](#two-pass-count-and-walk) |
| 2 | [Odd Even Linked List](#2-odd-even-linked-list) | Medium | O(n) | O(1) | [Odd/Even Chain Split](#oddeven-chain-split)        |
| 3 | [Reverse Linked List](#3-reverse-linked-list) | Easy | O(n) | O(1)* | [Pointer Reversal](#pointer-reversal)               |
| 4 | [Maximum Twin Sum of a Linked List](#4-maximum-twin-sum-of-a-linked-list) | Medium | O(n) | O(n)* | [Deque (Both Ends)](#deque-both-ends)               |

*\* O(1) for optimised solution, O(n) for deque solution*

---

### 1. Delete the Middle Node of a Linked List

**Approach:** Two-pass — first traverse the list to count nodes, then walk to the node before the middle and skip over it by rewiring `prev.next = prev.next.next`.

**Time Complexity:** O(n) — two passes through the list.

**Space Complexity:** O(1) — only a few pointer variables.

**Pattern:** [Two-Pass Count and Walk](#two-pass-count-and-walk) — first pass gathers size, second pass acts on a specific position.

**Key Insight:** We only need the node *before* the middle to perform the deletion. Walking `midIndex - 1` steps from the head lands exactly on that node.

**Code:**
```java
if (head.next == null) return null;

int counter = 1;
ListNode node = head;
while (node.next != null) {
    node = node.next;
    counter++;
}
int midIndex = counter / 2;

ListNode prev = head;
for (int i = 0; i < midIndex - 1; i++) {
    prev = prev.next;
}
prev.next = prev.next.next;

return head;
```

---

### 2. Odd Even Linked List

**Approach:** Use two pointers — `odd` walks odd-indexed nodes, `even` walks even-indexed nodes. Save `evenHead` before the loop. In each iteration, rewire `odd.next` and `even.next` to skip alternating nodes. After the loop, connect the chains with `odd.next = evenHead`.

**Time Complexity:** O(n) — single pass through the list.

**Space Complexity:** O(1) — only three pointer variables, no extra data structures.

**Pattern:** [Odd/Even Chain Split](#oddeven-chain-split) — split the list into two interleaved chains, then reconnect.

**Key Insight:** Saving `evenHead` before the loop is essential — without it, we lose the reference to the start of the even chain after rewiring pointers.

**Code:**
```java
if (head == null || head.next == null) return head;

ListNode odd = head;
ListNode even = head.next;
ListNode evenHead = even;

while (even != null && even.next != null) {
    odd.next = even.next;
    odd = odd.next;

    even.next = odd.next;
    even = even.next;
}

odd.next = evenHead;
return head;
```

---

### 3. Reverse Linked List

**Approach 1 (Deque):** Push all values onto a deque with front insertion (reverses order). Second pass overwrites each node's value by polling from the deque.

**Approach 2 (Pointer Reversal — Optimised):** Maintain three pointers: `prev`, `curr`, `next`. At each step, save `next`, flip `curr.next` to point backwards, then advance both pointers. When `curr` reaches null, `prev` is the new head.

**Time Complexity:** O(n) — each node visited once (or twice for deque approach).

**Space Complexity:** O(n) for deque, O(1) for pointer reversal.

**Pattern:** [Pointer Reversal](#pointer-reversal) — flip each link direction one node at a time.

**Key Insight:** You must save `curr.next` before overwriting it — otherwise you lose the reference to the rest of the list.

**Code (Deque):**
```java
ArrayDeque<Integer> deque = new ArrayDeque<>();

ListNode curr = head;
while (curr != null) {
    deque.offerFirst(curr.val);
    curr = curr.next;
}

curr = head;
while (curr != null) {
    curr.val = deque.poll();
    curr = curr.next;
}

return head;
```

**Code (Optimised):**
```java
ListNode prev = null;
ListNode curr = head;

while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}

return prev;
```

---

### 4. Maximum Twin Sum of a Linked List

**Approach:** Load all node values into a deque. Then simultaneously remove from both ends, computing the twin sum at each step and tracking the maximum.

**Time Complexity:** O(n) — each node visited once.

**Space Complexity:** O(n) for the deque approach, O(1) for the optimised slow/fast + reverse approach.

**Pattern:** [Deque (Both Ends)](#deque-both-ends) — use a double-ended queue to pair first/last elements efficiently.

**Key Insight:** Twin pairs are symmetric — node `i` pairs with node `n-1-i`. A deque lets you consume from both ends simultaneously. For O(1) space, use slow/fast pointers to find the middle, reverse the second half, then walk both halves.

**Code (Deque):**
```java
ArrayDeque<Integer> deque = new ArrayDeque<>();
ListNode temp = head;
while (temp != null) {
    deque.addLast(temp.val);
    temp = temp.next;
}

int midIndex = deque.size() / 2;
int maxSum = 0;
for (int i = 0; i < midIndex; i++) {
    int fValue = deque.removeFirst();
    int sValue = deque.removeLast();
    maxSum = Math.max(maxSum, fValue + sValue);
}
return maxSum;
```

---

## Binary Tree - DFS

### Quick Reference

| # | Problem | Difficulty | Time | Space | Pattern |
|---|---------|------------|------|-------|---------|
| 1 | [Maximum Depth of Binary Tree](#1-maximum-depth-of-binary-tree) | Easy | O(n) | O(h) | [Recursive DFS](#recursive-dfs) |
| 2 | [Leaf-Similar Trees](#2-leaf-similar-trees) | Easy | O(n1+n2) | O(h1+h2+L) | [Recursive DFS](#recursive-dfs) |
| 3 | [Count Good Nodes in Binary Tree](#3-count-good-nodes-in-binary-tree) | Medium | O(n) | O(h) | [Recursive DFS](#recursive-dfs) |
| 4 | [Path Sum III](#4-path-sum-iii) | Medium | O(n) | O(n) | [Prefix Sum + DFS](#prefix-sum--dfs) |
| 5 | [Longest ZigZag Path in a Binary Tree](#5-longest-zigzag-path-in-a-binary-tree) | Medium | O(n) | O(h) | [Recursive DFS](#recursive-dfs) |
| 6 | [Lowest Common Ancestor of a Binary Tree](#6-lowest-common-ancestor-of-a-binary-tree) | Medium | O(n) | O(h) | [Post-order DFS](#post-order-dfs) |

---

### 1. Maximum Depth of Binary Tree

**Approach:** Recursive DFS — at each node, recursively compute the depth of the left and right subtrees and return the greater one plus one. Base case returns 0 for a `null` node.

**Time Complexity:** O(n) — each node is visited exactly once.

**Space Complexity:** O(h) — recursion stack depth, where h is the tree height (O(log n) balanced, O(n) skewed).

**Pattern:** [Recursive DFS](#recursive-dfs) — decompose the problem into left/right subproblems and combine results on the way back up.

**Key Insight:** Maximum depth is defined structurally — you don't need to track a counter. Each return value naturally propagates the depth upward.

**Code:**
```java
public int maxDepth(TreeNode root) {
    if (Objects.isNull(root)) return 0;
    return calculateDepth(root);
}

private int calculateDepth(TreeNode node) {
    if (Objects.isNull(node)) return 0;
    int leftDepth = calculateDepth(node.left);
    int rightDepth = calculateDepth(node.right);
    return Math.max(leftDepth, rightDepth) + 1;
}
```

---

### 2. Leaf-Similar Trees

**Approach:** DFS collecting leaf values — traverse each tree and collect its leaf values (left to right) into a list. Two trees are leaf-similar if their leaf lists are equal.

**Time Complexity:** O(n1 + n2) — each node in both trees is visited exactly once.

**Space Complexity:** O(h1 + h2 + L) — recursion stack depths plus the two leaf lists, where h is tree height and L is the number of leaves.

**Pattern:** [Recursive DFS](#recursive-dfs) — collect information at leaf nodes during a full DFS traversal.

**Key Insight:** A node is a leaf when both children are `null`. Traversing left before right guarantees the collected sequence matches left-to-right reading order.

**Code:**
```java
public boolean leafSimilar(TreeNode root1, TreeNode root2) {
    if (Objects.isNull(root1) && Objects.isNull(root2)) return true;
    List<Integer> firstList = new ArrayList<>();
    collectLeafs(root1, firstList);

    List<Integer> secondList = new ArrayList<>();
    collectLeafs(root2, secondList);

    return firstList.equals(secondList);
}

private void collectLeafs(TreeNode treeNode, List<Integer> list) {
    if (Objects.isNull(treeNode.left) && Objects.isNull(treeNode.right)) {
        list.add(treeNode.val);
    }
    if (Objects.nonNull(treeNode.left)) collectLeafs(treeNode.left, list);
    if (Objects.nonNull(treeNode.right)) collectLeafs(treeNode.right, list);
}
```

---

### 3. Count Good Nodes in Binary Tree

**Approach:** Recursive DFS — pass the maximum value seen so far down each path. A node is good if its value is greater than or equal to that maximum. Collect good node values into a list and return its size.

**Time Complexity:** O(n) — each node is visited exactly once.

**Space Complexity:** O(h) — recursion stack depth, where h is the tree height (O(log n) balanced, O(n) skewed).

**Pattern:** [Recursive DFS](#recursive-dfs) — propagate path state (running max) downward and aggregate results on the way back up.

**Key Insight:** The root is always good since it has no ancestors. The path maximum is seeded with `root.val`, so the first comparison `node.val >= val` evaluates to `root.val >= root.val`, which is always true.

**Code:**
```java
public int goodNodes(TreeNode root) {
    if (Objects.isNull(root)) return 0;
    List<Integer> goodNodes = new ArrayList<>();
    countGoodNodes(root, root.val, goodNodes);
    return goodNodes.size();
}

private void countGoodNodes(TreeNode node, int val, List<Integer> goodNodes) {
    if (node.val >= val) {
        goodNodes.add(node.val);
        val = node.val;
    }
    if (Objects.nonNull(node.left)) countGoodNodes(node.left, val, goodNodes);
    if (Objects.nonNull(node.right)) countGoodNodes(node.right, val, goodNodes);
}
```

---

### 4. Path Sum III

**Problem:** Count the number of downward paths in a binary tree whose node values sum to `targetSum`. A path can start and end at any node (not just root or leaf).

---

#### Approach 1: Brute Force — O(n²)

For every node in the tree, count all paths starting at that node going downward. Two cooperating recursive methods:
- `countGoodPaths` — visits every node (outer recursion)
- `countFrom` — counts paths starting at a specific node (inner recursion)

**Time Complexity:** O(n²) — for each of the n nodes, `countFrom` traverses up to n nodes downward.

**Space Complexity:** O(h) — recursion stack depth.

**Code:**
```java
public int pathSumBruteForce(TreeNode root, int targetSum) {
    if (Objects.isNull(root)) return 0;
    return countGoodPaths(root, targetSum);
}

private int countGoodPaths(TreeNode root, int targetSum) {
    if (Objects.isNull(root)) return 0;
    return countFrom(root, targetSum)
         + countGoodPaths(root.left, targetSum)
         + countGoodPaths(root.right, targetSum);
}

private int countFrom(TreeNode node, long remaining) {
    if (Objects.isNull(node)) return 0;
    if (remaining - node.val == 0) {
        return 1 + countFrom(node.left, 0) + countFrom(node.right, 0);
    }
    return countFrom(node.left, remaining - node.val)
         + countFrom(node.right, remaining - node.val);
}
```

---

#### Approach 2: Prefix Sum + HashMap — O(n)

A single DFS tracks the running prefix sum from root to the current node. At each node, if `prefixSum - targetSum` was seen earlier on the path, the segment between that point and the current node is a valid path. The map is seeded with `{0: 1}` to handle paths that start at the root. Backtracking removes the current node's prefix sum so sibling subtrees are not affected.

**Time Complexity:** O(n) — each node is visited exactly once.

**Space Complexity:** O(n) — HashMap stores at most n prefix sums.

**Key Insight:** If the prefix sum at node B is `S` and `S - targetSum` appeared at some ancestor node A, then the path from A+1 to B sums to `targetSum`.

**Code:**
```java
public int pathSum(TreeNode root, int targetSum) {
    if (Objects.isNull(root)) return 0;
    Map<Long, Integer> map = new HashMap<>();
    map.put(0L, 1);
    return dfs(root, 0, targetSum, map);
}

private int dfs(TreeNode node, long prefixSum, int targetSum, Map<Long, Integer> map) {
    if (Objects.isNull(node)) return 0;
    long currentPrefixSum = prefixSum + node.val;
    int totalCount = map.getOrDefault(currentPrefixSum - targetSum, 0);
    map.put(currentPrefixSum, map.getOrDefault(currentPrefixSum, 0) + 1);
    totalCount += dfs(node.left, currentPrefixSum, targetSum, map)
               + dfs(node.right, currentPrefixSum, targetSum, map);
    int count = map.get(currentPrefixSum);
    if (count == 1) map.remove(currentPrefixSum);
    else map.put(currentPrefixSum, count - 1);
    return totalCount;
}
```

---

### 5. Longest ZigZag Path in a Binary Tree

**Approach:** Recursive DFS — pass the arrival direction (`isLeft`) and current zigzag length down at each step. One child continues the zigzag (`length + 1`), the other resets it (`length = 1`). When a null node is reached, the path ended at its parent, so return `length - 1`.

**Time Complexity:** O(n) — each node is visited exactly once.

**Space Complexity:** O(h) — recursion stack depth, where h is the tree height (O(log n) balanced, O(n) skewed).

**Pattern:** [Recursive DFS](#recursive-dfs) — propagate path state (direction + length) downward and take the max on the way back up.

**Key Insight:** At each node, going in the opposite direction from how you arrived continues the zigzag; going in the same direction resets it. Both children are always explored so no potential path is missed.

**Code:**
```java
public int longestZigZag(TreeNode root) {
    if (Objects.isNull(root)) return 0;
    return dfs(root, true, 0);
}

private int dfs(TreeNode node, boolean isLeft, int length) {
    if (Objects.isNull(node)) return length - 1;
    int leftLength  = isLeft ? 1 : length + 1;
    int rightLength = isLeft ? length + 1 : 1;
    return Math.max(dfs(node.left, true, leftLength), dfs(node.right, false, rightLength));
}
```

---

### 6. Lowest Common Ancestor of a Binary Tree

**Approach:** Post-order DFS — recurse into both subtrees first, then decide at the current node. If the current node is `p` or `q`, return it. If both left and right return non-null, the current node is the LCA. Otherwise bubble up whichever side found something.

**Time Complexity:** O(n) — each node is visited at most once.

**Space Complexity:** O(h) — recursion stack depth, where h is the tree height (O(log n) balanced, O(n) skewed).

**Pattern:** [Post-order DFS](#post-order-dfs) — results from both subtrees are needed before making a decision at the current node.

**Key Insight:** A node is the LCA if `p` is found in one subtree and `q` in the other, OR if the node itself is `p` or `q` (since a node is a descendant of itself).

**Code:**
```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (Objects.isNull(root)) return null;
    if (Objects.equals(root.val, p.val) || Objects.equals(root.val, q.val)) return root;
    return dfs(root, p, q);
}

private TreeNode dfs(TreeNode node, TreeNode p, TreeNode q) {
    if (Objects.isNull(node)) return null;
    if (Objects.equals(node.val, p.val) || Objects.equals(node.val, q.val)) return node;
    TreeNode left  = dfs(node.left,  p, q);
    TreeNode right = dfs(node.right, p, q);
    if (Objects.nonNull(left) && Objects.nonNull(right)) return node;
    return Objects.nonNull(left) ? left : right;
}
```

---

## Binary Tree - BFS

### Quick Reference

| # | Problem | Difficulty | Time | Space | Pattern |
|---|---------|------------|------|-------|---------|
| 1 | [Binary Tree Right Side View](#1-binary-tree-right-side-view) | Medium | O(n) | O(w) | [BFS Level-Order](#bfs-level-order) |

---

### 1. Binary Tree Right Side View

**Approach:** BFS level-order traversal — process nodes level by level using a queue. For each level, snapshot its size before iterating so you know exactly when you reach the last node. The last node in each level is the rightmost visible node from the right side.

**Time Complexity:** O(n) — each node is visited exactly once.

**Space Complexity:** O(w) — where w is the maximum width of the tree (maximum number of nodes at any single level).

**Pattern:** [BFS Level-Order](#bfs-level-order) — snapshot queue size to process exactly one level per outer iteration.

**Key Insight:** You don't need to track direction or depth explicitly. Snapshotting `size = queue.size()` before the inner loop isolates each level — the node at index `i == size - 1` is always the rightmost node of that level.

**Code:**
```java
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (Objects.isNull(root)) return res;

    Deque<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            if (i == size - 1) res.add(node.val);
            if (Objects.nonNull(node.left))  queue.offer(node.left);
            if (Objects.nonNull(node.right)) queue.offer(node.right);
        }
    }
    return res;
}
```

---

## Key Patterns

### BFS Level-Order

**When to use:** Tree problems that require processing nodes level by level — right/left side views, level averages, level maximums, zigzag traversal.

**How it works:** Enqueue the root, then repeatedly snapshot the current queue size before the inner loop. Process exactly that many nodes (one full level), enqueue their children, then move to the next level.

**Template:**
```java
void bfs(TreeNode root) {
    if (root == null) return;
    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        int size = queue.size();           // snapshot current level size
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            // process node (e.g. last node: i == size - 1)
            if (node.left  != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }
}
```

---

### Recursive DFS

**When to use:** Tree problems where the answer at each node depends on results from its subtrees (depth, path sums, leaf sequences, etc.).

**How it works:** Recurse into left and right children, then combine their results on the way back up. Base case handles `null` nodes.

**Template:**
```java
int dfs(TreeNode node) {
    if (node == null) return BASE_VALUE;

    int left = dfs(node.left);
    int right = dfs(node.right);

    return combine(left, right, node.val);
}
```

---

### Post-order DFS

**When to use:** Tree problems where you need information from **both** subtrees before you can make a decision at the current node (e.g., LCA, checking subtree properties).

**How it works:** Recurse into left and right children first, then combine their results at the current node. The decision is made on the way **back up**, not on the way down.

**Template:**
```java
TreeNode dfs(TreeNode node) {
    if (node == null) return null;
    TreeNode left  = dfs(node.left);
    TreeNode right = dfs(node.right);
    // decide based on left and right results
    return ...;
}
```

---

### Prefix Sum + DFS

**When to use:** Tree path problems where paths can start at any node, not just the root — especially when you need to count paths summing to a target in O(n).

**How it works:** Track the running prefix sum from root to the current node. At each node, look up `prefixSum - targetSum` in a HashMap — each occurrence means a valid path ends here. Add the current prefix sum to the map before recursing, and remove it after (backtrack) so sibling subtrees only see prefix sums from their own root-to-node path. Seed the map with `{0: 1}` to handle paths starting at the root.

**Template:**
```java
int dfs(TreeNode node, long prefixSum, int target, Map<Long, Integer> map) {
    if (node == null) return 0;
    long current = prefixSum + node.val;
    int count = map.getOrDefault(current - target, 0);
    map.merge(current, 1, Integer::sum);
    count += dfs(node.left, current, target, map)
           + dfs(node.right, current, target, map);
    if (map.merge(current, -1, Integer::sum) == 0) map.remove(current);
    return count;
}
```

---

### Two Pointers

**When to use:** Problems involving pairs, comparing elements from different positions, or traversing from both ends.

**How it works:** Maintain two pointers that move based on conditions — either toward each other, or in the same direction at different speeds.

**Template:**
```java
int left = 0, right = arr.length - 1;
while (left < right) {
    if (condition) {
        // process
        left++;
        right--;
    } else if (leftCondition) {
        left++;
    } else {
        right--;
    }
}
```

---

### Odd/Even Chain Split

**When to use:** Linked list problems where you need to split nodes into two interleaved chains based on position (odd/even indices) and reconnect them.

**How it works:** Use two pointers — one walks odd-indexed nodes, the other walks even-indexed nodes. Save the even chain head before rewiring. After the loop, connect the odd chain tail to the even chain head.

**Template:**
```java
ListNode odd = head;
ListNode even = head.next;
ListNode evenHead = even; // save before rewiring

while (even != null && even.next != null) {
    odd.next = even.next;   // link odd to next odd
    odd = odd.next;

    even.next = odd.next;   // link even to next even
    even = even.next;
}

odd.next = evenHead; // reconnect chains
return head;
```

---

### Slow/Fast Pointers

**When to use:** In-place array modifications, finding subsequences, cycle detection, or finding the middle of a linked list.

**How it works:** Slow pointer marks a position (placement or match), fast pointer scans ahead. They move at different rates based on conditions.

**Template (Array):**
```java
int slow = 0, fast = 0;
while (fast < arr.length) {
    if (condition(arr[fast])) {
        // process at slow position
        slow++;
    }
    fast++;
}
```

**Template (Linked List — Find Middle):**
```java
ListNode slow = head, fast = head;
while (fast.next != null && fast.next.next != null) {
    slow = slow.next;       // 1 step
    fast = fast.next.next;  // 2 steps
}
// slow is now at the end of the first half
// slow.next is the start of the second half
```

---

### Fixed-Size Sliding Window

**When to use:** Find maximum/minimum/count in all subarrays of size k.

**How it works:** Initialize window with first k elements. Slide by adding entering element and removing leaving element. Track result across all positions.

**Template:**
```java
// Initialize first window
int windowValue = 0;
for (int i = 0; i < k; i++) {
    windowValue += arr[i];
}

int result = windowValue;
for (int i = k; i < arr.length; i++) {
    windowValue += arr[i] - arr[i - k];  // add entering, remove leaving
    result = Math.max(result, windowValue);
}
```

---

### Variable-Size Sliding Window

**When to use:** Find longest/shortest subarray satisfying a condition.

**How it works:** Expand window with right pointer. When constraint is violated, shrink from left until valid. Track result when window is valid.

**Template:**
```java
int left = 0, result = 0;

for (int right = 0; right < arr.length; right++) {
    // expand: add arr[right] to window state
    
    while (/* window is invalid */) {
        // shrink: remove arr[left] from window state
        left++;
    }
    
    // window is valid, update result
    result = Math.max(result, right - left + 1);
}
```

---

### Running Prefix Sum

**When to use:** Problems involving cumulative sums, altitude changes, or finding balance points.

**How it works:** Maintain a running total as you iterate. Use it to compute results without storing all intermediate values.

**Template:**
```java
int prefixSum = 0;
int result = 0;

for (int num : nums) {
    prefixSum += num;
    result = Math.max(result, prefixSum);
}
```

---

### Two-Pass Scan

**When to use:** When each element's result depends on some global property of the array (max, min, sum, etc.).

**How it works:** First pass computes the global value. Second pass uses it to calculate results.

**Template:**
```java
// Pass 1: gather global info
int max = 0;
for (int num : nums) {
    max = Math.max(max, num);
}

// Pass 2: use it
for (int num : nums) {
    result.add(num >= max);
}
```

---

### Greedy

**When to use:** When making the locally optimal choice at each step leads to a globally optimal solution.

**How it works:** At each step, pick the best available option without reconsidering past choices. Works when local decisions don't block better future choices.

**Template:**
```java
for (int i = 0; i < arr.length; i++) {
    if (isValid(i)) {
        takeAction(i);
    }
}
```

---

### Prefix/Suffix

**When to use:** When each element's answer depends on all other elements — everything to its left and right.

**How it works:** Precompute cumulative values from the start (prefix) and from the end (suffix). Combine them for the final result.

**Why start with 1:** 1 is the multiplicative identity — "nothing to multiply" means multiply by 1.

**Template:**
```java
// Prefix: left to right
prefix[0] = 1;
for (int i = 1; i < n; i++) {
    prefix[i] = prefix[i - 1] * nums[i - 1];
}

// Suffix: right to left
suffix[n - 1] = 1;
for (int i = n - 2; i >= 0; i--) {
    suffix[i] = suffix[i + 1] * nums[i + 1];
}

// Combine
result[i] = prefix[i] * suffix[i];
```

---

### Read/Write Pointers

**When to use:** In-place array modification where output size ≤ input size.

**How it works:** Read pointer scans ahead, write pointer trails behind overwriting the array. Safe because compressed/filtered output never exceeds original.

**Template:**
```java
int write = 0, read = 0;
while (read < arr.length) {
    // process at read position
    // write result to arr[write++]
    // advance read
}
return write; // new length
```

---

### Digit Manipulation

**When to use:** Problems involving reversing numbers, extracting digits, or building numbers digit by digit.

**How it works:** Use `% 10` to extract last digit, `/ 10` to remove it. Build result with `result * 10 + digit`.

**Template:**
```java
int result = 0;
while (number != 0) {
    int digit = number % 10;
    result = result * 10 + digit;
    number /= 10;
}
```

---

### String Manipulation

**When to use:** Problems requiring splitting, reversing, or transforming strings.

**How it works:** Use built-in methods like `split()`, `trim()`, `StringBuilder` to transform and rebuild.

**Template:**
```java
String[] parts = s.trim().split("\\s+");
StringBuilder result = new StringBuilder();
for (String part : parts) {
    // transform and append
}
return result.toString();
```

---

### Stack (LIFO)

**When to use:** Problems where the most recent element must be removed first (undo, nested structures, collision resolution).

**How it works:** Push items as they come in, and pop when they are resolved. The last item added is the first removed.

**Template:**
```java
Deque<Integer> stack = new ArrayDeque<>();
stack.push(1);
stack.push(2);

int top = stack.peek(); // 2
stack.pop();            // removes 2
```

---

### Queue (FIFO)

**When to use:** Problems where the oldest element must be removed first (time windows, BFS, scheduling).

**How it works:** Add items to the back, remove from the front. The first item added is the first removed — ideal for expiring old entries in order.

**Template:**
```java
Deque<Integer> queue = new ArrayDeque<>();
queue.offer(1);   // add to back
queue.offer(2);

int front = queue.peek(); // 1
queue.poll();              // removes 1
```

---

### Deque (Both Ends)

**When to use:** Problems where you need to pair elements from opposite ends of a sequence — first with last, second with second-to-last, etc.

**How it works:** Load elements into a deque, then simultaneously remove from both ends. This naturally pairs symmetric positions without needing index calculations.

**Template:**
```java
Deque<Integer> deque = new ArrayDeque<>();
// load all elements
for (int val : values) {
    deque.addLast(val);
}

// pair from both ends
while (deque.size() > 1) {
    int first = deque.removeFirst();
    int last = deque.removeLast();
    // process pair (first, last)
}
```

---

### Two-Pass Count and Walk

**When to use:** Linked list problems where you need a node at a specific position but don't know the list length (e.g., delete the middle node, find the k-th node from end).

**How it works:** First pass traverses the entire list to count nodes. Second pass walks to the target position using the count.

**Template:**
```java
// Pass 1: count nodes
int count = 0;
ListNode node = head;
while (node != null) {
    node = node.next;
    count++;
}

// Pass 2: walk to target
int target = count / 2; // e.g., middle
ListNode prev = head;
for (int i = 0; i < target - 1; i++) {
    prev = prev.next;
}
// prev is now the node before the target
prev.next = prev.next.next; // skip target
```

---

### Pointer Reversal

**When to use:** Reversing a linked list (fully or partially), or problems that require traversing a list backwards without extra space.

**How it works:** Maintain three pointers: `prev`, `curr`, `next`. At each step, save `curr.next`, flip `curr.next` to point backwards, then advance both pointers. When done, `prev` is the new head.

**Template:**
```java
ListNode prev = null;
ListNode curr = head;

while (curr != null) {
    ListNode next = curr.next; // save next
    curr.next = prev;          // flip pointer
    prev = curr;               // advance prev
    curr = next;               // advance curr
}

return prev; // new head
```

---

### Frequency Map

**When to use:** Counting occurrences or grouping by value/character.

**How it works:** Use a hash map where keys are values/characters and values are counts. Update with `getOrDefault`.

**Template:**
```java
Map<Integer, Integer> counts = new HashMap<>();
for (int num : nums) {
    counts.put(num, counts.getOrDefault(num, 0) + 1);
}
```

---

### Set Difference

**When to use:** Finding elements that appear in one collection but not another.

**How it works:** Convert inputs to sets to remove duplicates, then remove all elements from the opposite set.

**Template:**
```java
Set<Integer> first = new HashSet<>(List.of(/* values */));
Set<Integer> second = new HashSet<>(List.of(/* values */));

List<Integer> onlyFirst = new ArrayList<>(first);
onlyFirst.removeAll(second);
```

---

### Frequency Distribution

**When to use:** Comparing strings or arrays by the multiset of their counts rather than exact positions.

**How it works:** Build frequency counts, ensure the same key set, then sort and compare the list/array of counts.

**Template:**
```java
int[] freq1 = new int[26];
int[] freq2 = new int[26];

for (char ch : word1.toCharArray()) {
    freq1[ch - 'a']++;
}
for (char ch : word2.toCharArray()) {
    freq2[ch - 'a']++;
}

for (int i = 0; i < 26; i++) {
    if ((freq1[i] == 0) != (freq2[i] == 0)) {
        return false;
    }
}

Arrays.sort(freq1);
Arrays.sort(freq2);
return Arrays.equals(freq1, freq2);
```

---

### Hashing Rows/Columns

**When to use:** Comparing whole rows/columns or fixed-length sequences where order matters.

**How it works:** Convert each row/column into a hashable key (list or string). Count rows in a map, then build each column key and add its row frequency.

**Template:**
```java
Map<List<Integer>, Integer> counts = new HashMap<>();

for (int[] row : grid) {
    List<Integer> key = Arrays.stream(row).boxed().toList();
    counts.put(key, counts.getOrDefault(key, 0) + 1);
}

int result = 0;
for (int c = 0; c < grid.length; c++) {
    int[] col = new int[grid.length];
    for (int r = 0; r < grid.length; r++) {
        col[r] = grid[r][c];
    }
    List<Integer> key = Arrays.stream(col).boxed().toList();
    result += counts.getOrDefault(key, 0);
}
```

---

### Math (Euclidean Algorithm)

**When to use:** Finding the greatest common divisor (GCD) of two numbers or lengths.

**How it works:** Repeatedly replace the larger number with the remainder of dividing it by the smaller. Stop when remainder is 0.

**Template:**
```java
int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
}
```

---

## Big O Essentials

Big O describes how runtime or space **scales** with input size. We drop constants because we care about growth rate, not exact measurements.

### Common Complexities

| Complexity | Name | Example |
|------------|------|---------|
| O(1) | Constant | Array access, variable swap |
| O(log n) | Logarithmic | Binary search, GCD |
| O(n) | Linear | Single loop, two pointers |
| O(n log n) | Linearithmic | Merge sort, heap sort |
| O(n²) | Quadratic | Nested loops, brute force |

### Why O(2n) = O(n)

If you loop twice, doubling the input still doubles the work. The growth rate is linear either way. Constants don't change the fundamental scaling.

### Quick Rules

| Code Pattern | Complexity |
|--------------|------------|
| Single loop through n elements | O(n) |
| Two separate loops (not nested) | O(n) |
| Nested loops | O(n²) |
| Halving each iteration | O(log n) |
| Loop with halving inside | O(n log n) |
