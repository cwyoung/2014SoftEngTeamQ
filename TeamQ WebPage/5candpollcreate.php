<!DOCTYPE html>
<html>
<?php
        $Display = 1;
        $pollName = $pollAccess = $pollDescription = $pollEnding = $pollCandidate1 = $pollCandidate2 =
                $pollCandidate3 = $pollCandidate4 = $pollCandidate5 = $codeSend = "";
        $hostname = 'localhost';
        $username = 'teamq';
        $password = 'dah4ieW1';
        $dbname = 'teamq';
        $con=mysqli_connect($hostname,$username,$password,$dbname);
        // Check connection
        if (mysqli_connect_errno())
        {
                echo "Connection to MySQL has failed.";
        }

        if(!empty($_REQUEST["pollname"]))
        {
                // escape variables for security
                $pollName = mysqli_real_escape_string($con, $_REQUEST['pollname']);
                $pollAccess = mysqli_real_escape_string($con, $_REQUEST['pollaccesscode']);
                $pollDescription = mysqli_real_escape_string($con, $_REQUEST['polldescription']);
                $pollEnding = mysqli_real_escape_string($con, $_REQUEST['pollending']);
                $pollCandidate1 = mysqli_real_escape_string($con, $_REQUEST['pollcand1name']);
                $pollCandidate2 = mysqli_real_escape_string($con, $_REQUEST['pollcand2name']);
                                $pollCandidate3 = mysqli_real_escape_string($con, $_REQUEST['pollcand3name']);
                $pollCandidate4 = mysqli_real_escape_string($con, $_REQUEST['pollcand4name']);
                                $pollCandidate5 = mysqli_real_escape_string($con, $_REQUEST['pollcand5name']);

                /*echo $pollName . '<br>';
                echo $pollAccess . '<br>';
                echo $pollDescription . '<br>';
                echo $pollEnding . '<br>';
                echo $pollCandidate1 . '<br>';
                echo $pollCandidate2 . '<br>';
                                echo $pollCandidate3 . '<br>';
                echo $pollCandidate4 . '<br>';
                                echo $pollCandidate5 . '<br>';*/


                $sql="INSERT INTO elections (ElectionName, accessCodeElection, descriptionElection, timeOutDate, Candidate1, Candidate2, Candidate3, Candidate4, Candidate5)
                VALUES ('$pollName', '$pollAccess', '$pollDescription','$pollEnding', '$pollCandidate1', '$pollCandidate2', '$pollCandidate3', '$pollCandidate4', '$pollCandidate5')";

                if (!mysqli_query($con,$sql))
                {
                        die('Error: ' . mysqli_error($con));
                }
        //echo "1 record added";
           mysqli_close($con);
           $Display = 2;
        }
        if (!empty($_POST["emails"]))
        {
                echo $codeSend . '<br>';
                $email_array = array();
                $emails = $_REQUEST['emails'];
                $email_array = explode(', ', $emails);
                $Display = 3;
        }


?>
<head>
        <title>
                Team Q Voting System Official Webpage!
        </title>
        <div align="center" id="header"><img src="UA_Logo_Horizontal.png" height="100" width="500"></div>
<script>
        function validateForm()
        {
                var pollName=document.forms["pollCreate"]["pollname"].value;
                if (pollName==null || pollName=="")
                {
                        document.getElementById("msg").innerHTML = "Please provide a name for your poll.";
                        return false;
                }

                var pollDescription=document.forms["pollCreate"]["polldescription"].value;
                if (pollDescription==null || pollDescription=="")
                {
                        document.getElementById("msg").innerHTML = "Please provide a description for your poll";
                        return false;
                }

                var pollEnd=document.forms["pollCreate"]["pollending"].value;
                if (pollEnd==null|| pollEnd=="")
                {
                        document.getElementById("msg").innerHTML = "Please set an ending date for your poll";
                        return false;
                }

                var candidateOne=document.forms["pollCreate"]["pollcand1name"].value;
                if (candidateOne==null ||candidateOne=="")
                {
                        document.getElementById("msg").innerHTML = "Please provide a name for candidate #1";
                        return false;
                }

                var candidateTwo=document.forms["pollCreate"]["pollcand2name"].value;
                if (candidateTwo==null ||candidateTwo=="")
                {
                        document.getElementById("msg").innerHTML = "Please provide a name for candidate #2";
                        return false;
                }

                                var candidateThree=document.forms["pollCreate"]["pollcand3name"].value;
                if (candidateThree==null ||candidateThree=="")
                {
                        document.getElementById("msg").innerHTML = "Please provide a name for candidate #3";
                        return false;
                }

                var candidateFour=document.forms["pollCreate"]["pollcand4name"].value;
                if (candidateFour==null ||candidateFour=="")
                {
                        document.getElementById("msg").innerHTML = "Please provide a name for candidate #4";
                        return false;
                }
                                var candidateFive=document.forms["pollCreate"]["pollcand5name"].value;
                if (candidateFive==null ||candidateFive=="")
                {
                        document.getElementById("msg").innerHTML = "Please provide a name for candidate #5";
                        return false;
                }
        }

</script>
</head>
<hr>
<?php if ($Display == 1) { ?>
<body>
<strong><div align="center" id="msg"> </div></strong>
        <form name="pollCreate" method="post" action="5candpollcreate.php" onsubmit="return validateForm()">
                <h2 align="center">Create Poll</h2>
                <table align="center" border="1">
                        <tr> <td align="center"><b>Poll Name:</b></td><td align="center"><input type="text" name="pollname" id="pollname"></td>
                        <tr><td align="center"><b>Access Code:</b></br> (7 Characters Max) </td><td align="center"><input type="text" name="pollaccesscode" id="pollaccesscode" maxlength="7" size="7"> *Private poll only<br>
                        <tr><td align="center"><b>Poll Description:</b></td><td align="center"><textarea name="polldescription" id="polldescription" rows="10" cols="25"></textarea></td><br>
                        <tr><td align="center"><b>Poll Ending Date:</b></td><td align="center"><input type="date" name="pollending" id="pollending">
                        <tr><td align="center"><b>Candidate Name:</b></td><td align="center"><input type="text" name="pollcand1name" id="pollcand1name">
                        <tr><td align="center"><b>Candidate Name:</b></td><td align="center"><input type="text" name="pollcand2name" id="pollcand2name">
                                                <tr><td align="center"><b>Candidate Name:</b></td><td align="center"><input type="text" name="pollcand3name" id="pollcand3name">
                        <tr><td align="center"><b>Candidate Name:</b></td><td align="center"><input type="text" name="pollcand4name" id="pollcand4name">
                                                <tr><td align="center"><b>Candidate Name:</b></td><td align="center"><input type="text" name="pollcand5name" id="pollcand5name">
                        <tr><td bgcolor="black"></td><td align="center"><input type="submit" name="pollcreatebutton" value="Create Poll!"></td>
                </table>
        </form>
</body>
<?php }
 else if ($Display == 2) {
?>
<body>
        <h1 align="center"> For your reference the access code for the poll you have just created is: <?php echo $pollAccess ?> </h1>
        <form name="sendCode" method="post" action="5candpollcreate.php">
        <h2 align="center">Send Access Code to Voters!</h2></legend>
                <table align="center" border="1">
                        <tr><td align="center"><div><b>Your name:</b></td><td align="center"><input type="text" name="sendername" id="sendername" value="Tester"></td></tr>
                        <tr> <td align="center"><div><b>E-mail addresses:</b><br>Please separate emails by ", "</div></td><td><input type="text" name="emails"></td>
                        <tr> <td bgcolor="black"></td><td align="center"><input  type="submit" name="createbutton" value="Send Access Code!"></td>
                </table>
        </form>
</body>
<?php }
 else if($Display == 3)
{
        ?>
        <h1 align="center"> Thank you so much for using our website </h1>
        <?php
        echo $pollAccess;
        $subject = 'Poll Access Code';
        $body = 'Tester has sent you an access code to participate in poll '.$pollName.' is '. $pollAccess;
        $headers = 'Team Q Voting System';
        foreach ($email_array as $email)
        {
                mail($email, $subject, $body, $headers);
        }
        echo '<div align="center"> <p> An email was sent to the following people: </p> </div><br>';
        foreach($email_array as $em)
        {
                echo '<div align="center"> ' .$em. '<br></div>';
        }
} ?>
</html>
