<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Get Email Address</title>
    <style>
        body {
            font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
        }

        .container {
            display: flex;
            justify-content: space-around;
        }
        
        .app-header {
        background: lightblue;
        height: 4rem;
        line-height: 4rem;
        padding-left: 1rem;
        font-size: 2rem;
        font-weight: bold;
        }
        
        .form-container {
        padding: 1rem;
        margin: 1rem;
        width: 100%;
        }
        
        .form-title {
        background: lightblue;
        padding: 0.5rem 1rem;
        font-weight: bold;
        border-radius: 0.25rem 0.25rem 0rem 0rem;
        }
        
        .form-body {
        border-radius: 0rem 0rem 0.25rem 0.25rem;
        border: solid 1px #ccc;
        padding: 1rem;
        }
        
        .form-button {
        background-color: #ffa500;
        color: white;
        padding: 0.5rem 1rem;
        border-radius: 0.25rem;
        border: none;
        cursor: pointer;
        font-weight: bold;
        width: 8rem;
        font-size: 0.8rem;
        }
        
        .form-button:hover {
        background-color: #ff8c00;
        }
        
        .form-button--inactive {
        background-color: white;
        color: #666;
        border: solid 1px #ccc;
        }
        
        .form-button--inactive:hover {
        background-color: #ccc;
        }
        
        .form-inline-controls {
        display: flex;
        align-items: center;
        padding: 0.5rem;
        }
        
        .form-inline-controls--end {
        justify-content: flex-end;
        }
        
        .form-inline-controls > span {
        flex: 1;
        }
        
        .form-inline-controls > input {
        flex: 2;
        }
        
        
        .margin-left-1 {
        margin-left: 1rem;
        }
        
        .margin-top-1 {
        margin-top: 1rem;
        }
        
        
        .margin-top-2 {
        margin-top: 2rem;
        }
        
        .form-table {
        font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
        border-collapse: collapse;
        width: 100%;
        }
        
        .form-table td,
        .form-table th {
        border: 1px solid #ddd;
        padding: 0.5rem;
        }
        
        .form-table tr:nth-child(even) {
        background-color: #f2f2f2;
        }
        
        .form-table tr:hover {
        background-color: #ddd;
        }
        
        .form-table th {
        padding-top: 0.5rem;
        padding-bottom: 0.5rem;
        text-align: left;
        background-color: lightblue;
        }
    </style>
</head>
    <body>
        <header class="app-header">
            Welcome to Time Tracker!
        </header>

        <div class="container">
            <form method="get" action="processForm" class="form-container">
                <div class="form-title">
                    Retrieve time records:
                </div>

                <div class="form-body">
                    <div class="form-inline-controls">
                        <span>Email address:</span> <input type="email" name="personEmail" required />
                    </div>
                    <div class="form-inline-controls">
                        <span># of rows to fetch:</span> <input type="string" name="numRecordFetch" placeholder="10" />
                    </div>
                    <div class="form-inline-controls form-inline-controls--end margin-top-1">
                        <button type="submit" name="fetchDetails" class="form-button">Retrieve</button>
                    </div>
                </div>
            </form>

            <form method="post" action="processForm" class="form-container">
                <div class="form-title">
                    Save time record:
                </div>

                <div class="form-body">
                    <div class="form-inline-controls">
                        <span>Email address:</span><input type="email" name="personEmail" required/>
                    </div>
                    <div class="form-inline-controls">
                        <span>Start timestamp:</span><input type="datetime-local" id="startTime" name="startTime" required/>
                    </div>
                    <div class="form-inline-controls">
                        <span>End timestamp:</span><input type="datetime-local" id="endTime" name="endTime" required/>
                    </div>
                    <div class="form-inline-controls form-inline-controls--end margin-top-1">
                        <button type="button" class="form-button form-button--inactive">Cancel</button>
                        <button type="submit" name="saveDetails" class="form-button margin-left-1">Save</button>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>