Some complex algorithms that I have solved

<h1>Stable Matching</h1>
  <ul>
    <li><b>Idea:</b> Given an instance of <i>StableMatching</i> determine the number of stable matches.</li>
    <li><b>Input Format:</b> The input File will be called <i>input1.txt</i> and be in the same directory as the java and class Files. Line <i>0</i> will be a single integer <i>n</i>, the number of men (or women). Lines <i>1</i> to <i>n</i> will be the preferences of the <i>n</i> men where each line is a space separated permutation of <i>{1,2,...,n}</i>. Lines <i>n + 1</i> to <i>n + n</i> will be the preferences of the <i>n</i> women where each line is a space separated permutation of <i>{1,2,...,n}</i>.</li>
    <li><b>Output:</b> A single number which is the number of different stable matches.</li>
    <li><b>Examples:</b> <br/>If the input was <br/>2<br/>1 2<br/>1 2<br/>1 2<br/>1 2<br/>then the output woud be<br/>1<br/>If the input was<br/>2<br/>1 2<br/>2 1<br/>2 1<br/>1 2<br/>then the output woud be<br/>2</li>
    </ul>
    
<hr>

<h1>Closest Pair of Points</h1>
  <ul>
    <li><b>Idea:</b> Given an instance of <i>ClosestPairOfPoints</i> determine minimum distance between any <i>2</i> points with a <i>O(n lg(n))</i> time algorithm.</li>
    <li><b>Input Format:</b> The input File will be called <i>input2.txt</i> and be in the same directory as the java and class Files. Each line will be two integer values separated by whitespace which represents the <i>X</i> and <i>Y</i> values of the points.</li>
    <li><b>Output:</b> A single number which is the square of the distance between the closest pair of points (the reason I want the square is to avoid precision errors).</li>
    <li><b>Examples:</b> <br/>If the input was <br/>14 3<br/>1 1<br/>5 10<br/>13 0<br/>then the output would be<br/>10<br/>(since the closest pair of points are <i>(13,0)</i> and <i>(14,3)</i> yielding <i>1<sup>2</sup> + 3<sup>2</sup></i>).</li>
    </ul>
