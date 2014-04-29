<!DOCTYPE html>
<html>
<head>
        <title>
                Team Q Voting System Official Webpage!
        </title>
        <div align="center" id="header"><img src="UA_Logo_Horizontal.png" height="100" width="500"></div>
<style>
table,td,th
{border:1px solid black;}
table
{width:100%;}
th
{height:50px;}
</style>
</head>
<h1> Results will be displayed here!!!!</h1>
<?php
echo 'Results.php';
$poll_id = $_REQUEST['pollid'];
echo '<br>' . $poll_id;

$host = 'localhost';
$username = 'teamq';
$password = 'dah4ieW1';

$con=mysqli_connect($host, $username, $password, $username);
// Check connection
if (mysqli_connect_errno()) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$result = mysqli_query($con,"SELECT Candidate1, Candidate2, Candidate3, Candidate4, Candidate5, Candidate6, Candidate7, Candidate8 FROM elections WHERE ElectionID = '".$poll_id."' ");
echo "<table border='1'>
<tr>
<th>Candidate 1</th>
<th>Candidate 2</th>
<th>Candidate 3</th>
<th>Candidate 4</th>
<th>Candidate 5</th>
<th>Candidate 6</th>
<th>Candidate 7</th>
<th>Candidate 8</th>
</tr>";
while($row = mysqli_fetch_array($result)) {
  echo "<tr>";
  echo "<td>" . $row['Candidate1'] . "</td>";
  echo "<td>" . $row['Candidate2'] . "</td>";
  echo "<td>" . $row['Candidate3'] . "</td>";
  echo "<td>" . $row['Candidate4'] . "</td>";
  echo "<td>" . $row['Candidate5'] . "</td>";
  echo "<td>" . $row['Candidate6'] . "</td>";
  echo "<td>" . $row['Candidate7'] . "</td>";
  echo "<td>" . $row['Candidate8'] . "</td>";
  echo "</tr>";
}
$result2 = mysqli_query($con,"SELECT votesCand1, votesCand2, votesCand3, votesCand4, votesCand5, votesCand6, votesCand7, votesCand8 FROM elections WHERE ElectionID = '".$poll_id."' ");
echo "<table border='1'>";
while($row = mysqli_fetch_array($result2)) {
  echo "<tr>";
  echo "<td>" . $row['votesCand1'] . "</td>";
  echo "<td>" . $row['votesCand2'] . "</td>";
  echo "<td>" . $row['votesCand3'] . "</td>";
  echo "<td>" . $row['votesCand4'] . "</td>";
  echo "<td>" . $row['votesCand5'] . "</td>";
  echo "<td>" . $row['votesCand6'] . "</td>";
  echo "<td>" . $row['votesCand7'] . "</td>";
  echo "<td>" . $row['votesCand8'] . "</td>";
  echo "</tr>";
}
mysqli_close($con);
?>
</html>
