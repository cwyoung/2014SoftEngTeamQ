<!DOCTYPE html>
<html>
<head>
        <title>
                Team Q Voting System Official Webpage
        </title>
        <div align="center" id="header"> <img src="UA_Logo_Horizontal.png" height="100" width="500"></div>
</head>
<h1 align="center"> Team Q Voting System</h1>
<hr>
<body>
<div align="center">
        <form action="initial.php"  action="get">
          <table align="center" border="1" width="350">
          <tr><td align="center"><b>Enter the number of candidates</b></td>
                 <td align="center"><select name="numCandidates">
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option></td>
           </tr>
           <tr><td bgcolor="black"></td><td><input type="submit" name="numCandidatesButton" value="Submit"></td></tr>
          </table>
                </div>
                </select>
</form>

<?php
        if (isset($_REQUEST['numCandidatesButton']))
                {
                        echo '<br>';
                        $user_selection = $_REQUEST['numCandidates'];
                        switch($user_selection)
                        {
                           case 2:
                           echo '<div align="center">'. 'You have selected <strong>' .$user_selection. '</strong> candidates for this election.';
                           ?>
                           <form action="2candpollcreate.php" action="get">
                                        <input align="center" type="submit" name="createPollDirect" value="Continue">
                                </form>
                           <?php
                           break;
                           case 3:
                           echo '<div align="center">'. 'You have selected <strong>' .$user_selection. '</strong> candidates for this election.';
                           ?>
                           <form action="3candpollcreate.php" action="get">
                                 <input align="center" type="submit" name="createPollDirect" value="Continue">
                                </form>
                           <?php
                           break;
                           case 4:
                           echo '<div align="center">'. 'You have selected <strong>' .$user_selection. '</strong> candidates for this election.';
                                                   ?>
                           <form action="4candpollcreate.php" action="get">
                                 <input align="center" type="submit" name="createPollDirect" value="Continue">
                                </form>
                           <?php
                           break;
                           case 5:
                           echo '<div align="center">'. 'You have selected <strong>' .$user_selection. '</strong> candidates for this election.';
                                                   ?>
                           <form action="5candpollcreate.php" action="get">
                                 <input align="center" type="submit" name="createPollDirect" value="Continue">
                                </form>
                           <?php
                           break;
                           case 6:
                           echo '<div align="center">'. 'You have selected <strong>' .$user_selection. '</strong> candidates for this election.';
                                                   ?>
                           <form action="6candpollcreate.php" action="get">
                                 <input align="center" type="submit" name="createPollDirect" value="Continue">
                                </form>
                           <?php
                           break;
                           case 7:
                           echo '<div align="center">'. 'You have selected <strong>' .$user_selection. '</strong> candidates for this election.';
                                                   ?>
                           <form action="7candpollcreate.php" action="get">
                                 <input align="center" type="submit" name="createPollDirect" value="Continue">
                                </form>
                           <?php
                           break;
                           case 8:
                           echo '<div align="center">'. 'You have selected <strong>' .$user_selection. '</strong> candidates for this election.';
                                                   ?>
                           <form action="8candpollcreate.php" action="get">
                                 <input align="center" type="submit" name="createPollDirect" value="Continue">
                                </form>
                           <?php
                           break;
                        }
                }
        ?>
<hr>
<div>
<form name="ViewResults" method="get" action="results.php">
    <h2 align="center">View Results Online</h2>
        <table align="center" border="1" width="350">
                <tr> <td align="center"><b>Poll ID:</b></td><td align="center"><input type="text" name="pollid"></td></tr>
                <tr> <td bgcolor="black"></td><td align="center"><input type="submit" name="createbutton" value="View Results"></td></tr>
                </table>
</form>
<div id="message"> </div>
</div>
<hr>
<div>
<form action="initial.php" method="POST" action="initial.php">
        <h2 align="center">Contact Us</h2>
        <table align="center" border="1" width="350">
    <tr><td align="center"><b>Name: </b></td><td align="center"><input type="text" name="contact_name"></td></tr>
    <tr><td align="center"><b>Email address: </b></td><td align="center"><input type="text" name="contact_email"></td></tr>
    <tr><td align="center"><b>Message:</b><td ><textarea name="contact_text" rows="7" cols="30"></textarea></td></tr>
    <tr><td bgcolor="black"></td><td align="center"><input type="submit" value="Send"></td></tr>
        </table>
</form>
</div>
<?php
if(isset($_POST['contact_name']) && isset ($_POST['contact_email']) && isset($_POST['contact_text']))
{
$contact_name = $_POST['contact_name'];
$contact_email = $_POST['contact_email'];
$contact_text = $_POST['contact_text'];
if (!empty($contact_name) && !empty($contact_email) && !empty($contact_text))
{
     $to_array = array('nanunezr@uark.edu', 'cwyoung@uark.edu', 'kqiu@uark.edu', 'pschoema@uark.edu', 'sdlowe@uark.edu', 'mbenavid@uark.edu');
     $subject = 'Contact form submitted';
     $body = $contact_text;
     $headers = 'From: ' . $contact_email;

   // mail function
    foreach($to_array as $to)
    {
        mail($to, $subject, $body, $headers);
    }
        echo '<strong><div align="center"> Thanks for contacting us we will be in contact soon!</div></strong>';
}
else
 {
        echo '<strong><div align="center"> Please fill out the form, all fields are required.</div></strong><br>';
 }
}
?>
</body>
</html>
