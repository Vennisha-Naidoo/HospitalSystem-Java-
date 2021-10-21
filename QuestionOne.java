import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.*;
import javax.swing.event.*;
import net.proteanit.sql.DbUtils;

public class QuestionOne {
    
    //global static variable declaration for statement, connection and resultset
    static Statement st;
    static Connection con;
    static ResultSet rs;
    
    //method for creating a database connection
    public static void DatabaseCon(){
        //declaration of try and catch
        try{
            //path used for connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalsystem", "vennisha", "vennisha123");
            //statement using connection to create a statement
            st = con.createStatement();
        }
        //catching any database connection exceptions
        catch (SQLException e) {
            //print exception, if one exists
            System.out.println(e.getMessage());
      }
    }
    
    //method declaration for login
    public static void Login(){
        //creating frame object with title
        JFrame myFrame = new JFrame("Login");
        //setting the frame to be visible
        myFrame.setVisible(true);
        //the frame has not specific layout
        myFrame.setLayout(null);
        //setting frame width and height
        myFrame.setSize(400, 250);
        //setting frame x and y co-ordinates
        myFrame.setLocation(500, 200);
        //exit button, on frame, works when clicked
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        //creating a string array, contatining users
        String arrUsers[] = {"Doctor", "Nurse", "Patient"};
        //declaration of combo boc and passing array as arguement
        //This allows for the users to be displayed in the combo box
        JComboBox cmbxUser = new JComboBox(arrUsers);
        //setting maximum bounds for the compoment
        //x and y co-ordinates and width and height
        cmbxUser.setBounds(90, 5, 200, 20);
        //adding combo box to the login frame
        myFrame.getContentPane().add(cmbxUser);
        
        //declaration of label, with set text
        JLabel lblUserID = new JLabel("User Identification Number");
        //setting bounds of label
        lblUserID.setBounds(10, 55, 200, 100);
        //adding label to the frame
        myFrame.getContentPane().add(lblUserID);
        
        //declaration of textfield
        JTextField txtUserID = new JTextField();
        //setting bound of the text field
        txtUserID.setBounds(200, 85, 165, 30);
        //adding component to the frame
        myFrame.getContentPane().add(txtUserID);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(10, 100, 100, 100);
        myFrame.getContentPane().add(lblPassword);
        
        JTextField txtPassword = new JTextField();
        txtPassword.setBounds(200, 130, 165, 30);
        myFrame.getContentPane().add(txtPassword);
        
        //declaration of button, with text
        JButton btnLogin = new JButton("Login");
        //setting boundd of button
        btnLogin.setBounds(90, 170, 200, 30);  
        //adding button to the frame
        myFrame.getContentPane().add(btnLogin);
        
        //button action event (when the button is clicked, the following will occur)
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //declaration and initialisation of variable
                //getting text from textfield
                String userID = txtUserID.getText().toString();
                String userPassword = txtPassword.getText().toString();
                
                //if first option in combo box is selected
                //option for doctor login
                if(cmbxUser.getSelectedIndex()==0){
                    //try declaration
                    try {
                        //resultset storing the current sql query that would be recieved
                        rs = st.executeQuery("select * from doctordetails");
                        
                        //while loop declaration
                        //next of resultset condition 
                        while(rs.next()){
                            
                            //checking if combo box selected option, user id and password matches
                            if(rs.getString("DoctorID").equals(userID) && rs.getString("DoctorPassword").equals(userPassword)){
                                //calling Doctor method and passing user id, for further uses
                                Doctor(userID);
                                //disposing of login frame
                                myFrame.dispose();
                                break;
                            //else if statement, if the user login details are incorrect
                            //if user id exists, with the selected user option but password is incorrect
                            }else if(rs.getString("DoctorID").equals(userID) || rs.getString("DoctorPassword").equals(userPassword)){
                                //JOptionpane, displaying a message in a message dialog box
                                JOptionPane.showMessageDialog(null, "Invalid login details.", "Error!",JOptionPane.INFORMATION_MESSAGE);
                                break;
                            //if loop is at end of resultset and does not exists, it displays an error, in a message dialog     
                            }else if (rs.isLast() == true){
                                JOptionPane.showMessageDialog(null, "User does not exist.", "Error!",JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                        }     
                    } catch (SQLException ex) {
                        Logger.getLogger(ex.getMessage());
                    }
                }//end of if cmbx ==0
                //login for the nurse 
                else if(cmbxUser.getSelectedIndex()==1){
                    try {
                        rs = st.executeQuery("select * from nursedetails");
                        
                        while(rs.next()){
                            
                            if(rs.getString("NurseID").equals(userID) && rs.getString("NursePassword").equals(userPassword)){
                                Nurse();
                                myFrame.dispose();
                                break;
                                
                            }else if(rs.getString("NurseID").equals(userID) || rs.getString("NursePassword").equals(userPassword)){
                                JOptionPane.showMessageDialog(null, "Invalid login details.", "Error!",JOptionPane.INFORMATION_MESSAGE); 
                                break;
                            }else if (rs.isLast() == true){
                                JOptionPane.showMessageDialog(null, "User does not exist.", "Error!",JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                        }     
                    } catch (SQLException ex) {
                        Logger.getLogger(ex.getMessage());
                    }
                }//end of else if cmbx==1
                //login for patients
                else if(cmbxUser.getSelectedIndex()==2){
                    try {
                        rs = st.executeQuery("select * from patientdetails");
                        
                        while(rs.next()){
                            
                            if(rs.getString("PatientID").equals(userID) && rs.getString("PatientPassword").equals(userPassword)){
                                Patient(userID);
                                myFrame.dispose();
                                break;
                                
                            }else if(rs.getString("PatientID").equals(userID) || rs.getString("PatientPassword").equals(userPassword)){
                                JOptionPane.showMessageDialog(null, "Invalid login details.", "Error!",JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }else if (rs.isLast() == true){
                                JOptionPane.showMessageDialog(null, "User does not exist.", "Error!",JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                        }     
                    } catch (SQLException ex) {
                        Logger.getLogger(ex.getMessage());
                    }
                }//end of else if cmbx==1
                
            }//end actionPerformed
        });
        
    }
    
    //method declaration for doctor, with user id passed as a parameter
    public static void Doctor(String x){
        
        //frame declaration for doctor
        JFrame myFrame = new JFrame("Doctor Access");
        //setting visibilty of frame
        myFrame.setVisible(true);
        //frame has no specific layout
        myFrame.setLayout(null);
        //setting frame to maximum size
        myFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setting x and y co-ordinates of frame
        myFrame.setLocation(0,0);
        //close of exit
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        //declaration of jtable
        JTable tbGeneral = new JTable();
        //declaration of scrollpane and adding the jtable to it
        JScrollPane srcpn = new JScrollPane(tbGeneral);
        //setting bounds of scrollpane
        srcpn.setBounds(200, 100, 1150, 500);
        //adding component to frame
        myFrame.getContentPane().add(srcpn);
        
        //button to view all appointments
        //declaration of button, with text
        JButton btnViewAppointments = new JButton("All Appointments");
        //seting bounds of button
        btnViewAppointments.setBounds(10, 100, 150, 30);
        //adding button to frame
        myFrame.getContentPane().add(btnViewAppointments);
        
        //buuton to view all doctor's patients
        JButton btnPatients = new JButton("All Patients Details");
        btnPatients.setBounds(10, 150, 150, 30);
        myFrame.getContentPane().add(btnPatients);
        
        //button to update patient diagnosis
        JButton btnUpdateDiagnosis = new JButton("Update Diagnosis");
        btnUpdateDiagnosis.setBounds(10, 565, 150, 30);
        myFrame.getContentPane().add(btnUpdateDiagnosis);
        
        //action click to view all appointments
        btnViewAppointments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //sql query being executed
                    rs = st.executeQuery("select PatientID, PatientName, PatientSurname, DoctorName, DoctorSurname, Date, Time, RoomNumber, Status, Symptoms, Diagnosis from appointmentdetails where DoctorID = "+x+"");
                    //adding resultset to jable
                    tbGeneral.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException ex) {
                    Logger.getLogger(ex.getMessage());
                }
            }
        });
        
        //action click to display all patients of the doctor that's logged in
        btnPatients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //resultset being recieved when query is executed
                    rs = st.executeQuery("select PatientID, PatientName, PatientSurname, PatientGender, PatientPhoneNumber from patientdetails");
                    //adding resultset to the jtable
                    tbGeneral.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException ex) {
                    Logger.getLogger(ex.getMessage());
                }
            }
        });
        
        //button click to update patient diagnosis
        btnUpdateDiagnosis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //getting selected row, from correct jtable
                int row = tbGeneral.getSelectedRow();
                
                //if jtable columns are less than 6, it is the incorrect table selected
                if(tbGeneral.getColumnCount()<6){
                    JOptionPane.showMessageDialog(null, "Incorrect table selected.", "Error!",JOptionPane.INFORMATION_MESSAGE);  
                }else{
                //if row is not selected
                if(row<0){
                    JOptionPane.showMessageDialog(null, "No row selected.", "Error!",JOptionPane.INFORMATION_MESSAGE);
                }else{
               //try, when correct table and row is selected
                try {    
                //getting value of patient id and storing in a string variable   
                String tbVal = (tbGeneral.getValueAt(row, 0)).toString();
                
                    //using patient id to find patient in patient table
                    rs = st.executeQuery("SELECT * FROM patientdetails WHERE PatientID = '"+tbVal+"'");
                    //while loop, looking for patient
                    while(rs.next()){
                        
                        //declaration of frame, for patient diagnosis
                        JFrame frmUpdateDiagnosis = new JFrame("Update Diagnosis");
                        //making frame visible
                        frmUpdateDiagnosis.setVisible(true);
                        //frame has no layout
                        frmUpdateDiagnosis.setLayout(null);
                        //setting width and height of frame
                        frmUpdateDiagnosis.setSize(300, 450);
                        //setting loctation of frame
                        frmUpdateDiagnosis.setLocation(500,200);
                        //close when exit button is clicked
                        frmUpdateDiagnosis.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        //label declaration
                        JLabel lblPName = new JLabel();
                        //setting text for label
                        lblPName.setText("Patient Name");
                        //setting bounds of label
                        lblPName.setBounds(10, 20, 100, 30);
                        //adding label to frame
                        frmUpdateDiagnosis.getContentPane().add(lblPName);
                        
                        //declaration of textfield
                        JTextField txtPatientName = new JTextField();
                        //getting resultset patient name and setting to textfield
                        txtPatientName.setText(rs.getString("PatientName"));
                        //setting bounds of textfield
                        txtPatientName.setBounds(120, 20, 150, 30);
                        //setting background of textfield to white
                        txtPatientName.setBackground(Color.white);
                        //setting the textfield to be uneditable
                        txtPatientName.setEditable(false);
                        //adding textfield to frame
                        frmUpdateDiagnosis.getContentPane().add(txtPatientName);
                        
                        JLabel lblPSurname = new JLabel();
                        lblPSurname.setText("Patient Surname");
                        lblPSurname.setBounds(10, 70, 100, 30);
                        frmUpdateDiagnosis.getContentPane().add(lblPSurname);
                        
                        JTextField txtPatientSurname = new JTextField();
                        txtPatientSurname.setText(rs.getString("PatientSurname"));
                        txtPatientSurname.setBounds(120, 70, 150, 30);
                        txtPatientSurname.setBackground(Color.white);
                        txtPatientSurname.setEditable(false);
                        frmUpdateDiagnosis.getContentPane().add(txtPatientSurname);
                        
                        JLabel lblDiagnosis = new JLabel();
                        lblDiagnosis.setText("Diagnosis");
                        lblDiagnosis.setBounds(10, 130, 100, 30);
                        frmUpdateDiagnosis.getContentPane().add(lblDiagnosis);
                        
                        //declaration of textarea
                        JTextArea txtaDiagnosis = new JTextArea();
                        //setting bounds of textarea
                        txtaDiagnosis.setBounds(120, 130, 150, 100);
                        //adding textarea ro frame
                        frmUpdateDiagnosis.getContentPane().add(txtaDiagnosis);
                        
                        JLabel lblStatus = new JLabel();
                        lblStatus.setText("Visit Status");
                        lblStatus.setBounds(10, 240, 100, 30);
                        frmUpdateDiagnosis.getContentPane().add(lblStatus);
                        
                        //declaration of radio buttons, with text
                        JRadioButton rbPending = new JRadioButton("Pending");
                        JRadioButton rbSeen = new JRadioButton("Seen");
                        JRadioButton rbCancelled = new JRadioButton("Cancelled");
                        //setting bounds of radiom buttons
                        rbPending.setBounds(120, 240, 150, 30);
                        rbSeen.setBounds(120, 270, 150, 30);
                        rbCancelled.setBounds(120, 300, 150, 30);
                        
                        //radio group declaration
                        ButtonGroup bgStatus = new ButtonGroup();
                        //ading radio buttons to the radio group
                        bgStatus.add(rbPending);
                        bgStatus.add(rbSeen);
                        bgStatus.add(rbCancelled);
                        //adding the radio buttons to the frame
                        frmUpdateDiagnosis.add(rbPending);
                        frmUpdateDiagnosis.add(rbSeen); 
                        frmUpdateDiagnosis.add(rbCancelled);
                        
                        //button declaration
                        JButton btnSave = new JButton("Save");
                        //setting bounds of button
                        btnSave.setBounds(170, 350, 100, 30);
                        //adding buton to the frame
                        frmUpdateDiagnosis.getContentPane().add(btnSave);
                        
                        //button action listener
                        btnSave.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                               
                                //getting text from diagnosis textarea
                                String Diagnosis = txtaDiagnosis.getText().toString();
                                //setting status to and empty string
                                String Status = "";
                                
                                //checking which radio button has been selected and setting a text to status variable
                                if(rbPending.isSelected()){
                                    Status = "Pending";
                                }else if(rbSeen.isSelected()){
                                    Status = "Seen";
                                }else if (rbCancelled.isSelected()){
                                    Status = "Cancelled";
                                }
                                
                                //try declaration
                                try {
                                    //executing update statements for diagnosis
                                    st.execute("UPDATE appointmentdetails SET Diagnosis = '"+Diagnosis+"' WHERE PatientID = '"+tbVal+"'");
                                    //executing update statements for status
                                    st.execute("UPDATE appointmentdetails SET Status = '"+Status+"' WHERE PatientID = '"+tbVal+"'");
                                    //displaying message dialog to confirm updates
                                    JOptionPane.showMessageDialog(null, "Patient diagnosed.", "Information",JOptionPane.INFORMATION_MESSAGE);
                                    //disposing of diagnosis frame
                                    frmUpdateDiagnosis.dispose();
                                } catch (SQLException ex) {
                                    Logger.getLogger(ex.getMessage());
                                }
                                
                            }
                        });
                        
                    }
                    
                    
                    
                } catch (SQLException ex) {
                    //catch, if try fails
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Information",JOptionPane.INFORMATION_MESSAGE);            
                }
            }
           }
           }
        });
        
        //logout declaration button 
        JButton btnLogOut = new JButton("Log Out");
        //setting bounds for button
        btnLogOut.setBounds(660, 630, 150, 30);
        //adding button to frame
        myFrame.getContentPane().add(btnLogOut);
        
        //action listener for logout
        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //calling loging function
                Login();
                //disposal of doctor frame
                myFrame.dispose();
            }
        });
    }
    
    //method declaration for nurse
    public static void Nurse(){
        
        //frame declaration
        JFrame frmNurse = new JFrame("Nurse Access");
        //setting properties of the frame
        frmNurse.setVisible(true);
        frmNurse.setLayout(null);
        frmNurse.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frmNurse.setLocation(0,0);
        frmNurse.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //jtable declaration
        JTable tbGeneral = new JTable();
        //scrollpane declaration
        //adding jtable to scrollpane
        JScrollPane srcpn = new JScrollPane(tbGeneral);
        //setting bounds of scrollpane
        srcpn.setBounds(200, 100, 1150, 500);
        //adding scrollpane to the frame
        frmNurse.getContentPane().add(srcpn);
        
        //declaration of buttons, setting bounds and adding buttons to the frame
        JButton btnViewAppointments = new JButton("All Appointments");
        btnViewAppointments.setBounds(10, 100, 150, 30);
        frmNurse.getContentPane().add(btnViewAppointments);
        
        JButton btnNewAppointment = new JButton("New Appointment");
        btnNewAppointment.setBounds(10, 150, 150, 30);
        frmNurse.getContentPane().add(btnNewAppointment);
        
        JButton btnPatients = new JButton("All Patients Details");
        btnPatients.setBounds(10, 200, 150, 30);
        frmNurse.getContentPane().add(btnPatients);
        
        JButton btnRegisterPatient = new JButton("Register Patient");
        btnRegisterPatient.setBounds(10, 250, 150, 30);
        frmNurse.getContentPane().add(btnRegisterPatient);
        
        JButton btnUpdateTreatment = new JButton("Update Treatment");
        btnUpdateTreatment.setBounds(10, 565, 150, 30);
        frmNurse.getContentPane().add(btnUpdateTreatment);
        
        //action listener, view all existing appointments
        btnViewAppointments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //try declaration
                try {
                    //resultset, storing executed query
                    rs = st.executeQuery("select PatientName, PatientSurname, PatientID, DoctorName, DoctorSurname, DoctorID, Date, Time, RoomNumber, Status, Symptoms, Diagnosis, Treatment from appointmentdetails");
                    //adding resultset to the jtable
                    tbGeneral.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException ex) {
                    Logger.getLogger(ex.getMessage());
                }
            }
        });
        
        //action listener, to create a new appointment
        btnNewAppointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //frame declaration, with title
                JFrame frmNewApp = new JFrame("New Appointment");
                //setting frame properties
                frmNewApp.setVisible(true);
                frmNewApp.setLayout(null);
                frmNewApp.setSize(400,650);
                frmNewApp.setLocation(500,70);
                frmNewApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                //label declaration with text
                JLabel lblPatientID = new JLabel("Patient ID");
                //set bounds of label
                lblPatientID.setBounds(20, 20, 150, 30);
                //adding label to the frame
                frmNewApp.getContentPane().add(lblPatientID);
                
                //declaration of textfield
                JTextField txtPatientID = new JTextField();
                //setting bounds of textfield
                txtPatientID.setBounds(150, 20, 210, 30);
                //adding textfield to frame
                frmNewApp.getContentPane().add(txtPatientID);
                
                JLabel lblPatientName = new JLabel("Patient Name");
                lblPatientName.setBounds(20, 70, 150, 30);
                frmNewApp.getContentPane().add(lblPatientName);
                
                JTextField txtPatientName = new JTextField();
                txtPatientName.setBounds(150, 70, 210, 30);
                frmNewApp.getContentPane().add(txtPatientName);
                
                JLabel lblPatientSurname = new JLabel("Patient Surname");
                lblPatientSurname.setBounds(20, 120, 150, 30);
                frmNewApp.getContentPane().add(lblPatientSurname);
                
                JTextField txtPatientSurname = new JTextField();
                txtPatientSurname.setBounds(150, 120, 210, 30);
                frmNewApp.getContentPane().add(txtPatientSurname);
              
                //combo box declaration
                JComboBox cmbxDoctors =  new JComboBox();
                
                //real-time search, for patient id
                txtPatientID.addKeyListener(new KeyListener(){
                    public void keyPressed(KeyEvent event) {
                    }
                    
                    //when each key is pressed, it checks database
                    public void keyReleased(KeyEvent event) {
                        //try...catch statement
                        try{
                            //resultset storing the executed query
                            rs = st.executeQuery("SELECT * FROM patientdetails WHERE PatientID = '"+txtPatientID.getText()+"'");
                            
                            //if the patient id exists
                            if(rs.next()==true){
                                //iterate through all columns and display the required patient details
				txtPatientName.setText(rs.getString("PatientName"));
                                txtPatientSurname.setText(rs.getString("PatientSurname"));
                            }else{
                                //if patient id does not exists, set text to an empty string
				txtPatientName.setText("");
				txtPatientSurname.setText("");
                            }
			}catch(Exception e){	
                            //display message dialog, patient not found, if patient id does not exists
                            JOptionPane.showMessageDialog(null, "Patient Not Found.", "MessageBox", JOptionPane.INFORMATION_MESSAGE);
			}
                    }
       
                    public void keyTyped(KeyEvent event) { 
                    }
                });
                
                try {
                    //resultset executing query
                    rs = st.executeQuery("SELECT * FROM doctordetails");
                    
                    //label declaration
                    JLabel lblSpecialisation = new JLabel("Specialisation");
                    //setting bounds of declaration
                    lblSpecialisation.setBounds(20, 170, 150, 30);
                    //adding label to frame
                    frmNewApp.getContentPane().add(lblSpecialisation);
                    
                    //declaration of combo box
                    JComboBox cmbxSpecialisation =  new JComboBox();
                    //while loop declaration
                    while(rs.next()){
                        //adding EXISTING specialisations, in the hospital
                        cmbxSpecialisation.addItem(rs.getString("Specialisation"));
                    }
                    //setting bounds of combo box
                    cmbxSpecialisation.setBounds(150, 170, 210, 30);
                    //adding combo box to frame
                    frmNewApp.getContentPane().add(cmbxSpecialisation);

                    JLabel lblDoctor = new JLabel("Select Doctor");
                    lblDoctor.setBounds(20, 220, 150, 30);
                    frmNewApp.getContentPane().add(lblDoctor);
                    
                    //when item in combo box is selected
                    cmbxSpecialisation.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent arg0) {
                            try {
                                //remove items from combo box each time, when new specialisation is selected
                                cmbxDoctors.removeAllItems();
                                //resultset, executing query, where the specialisation extists
                                rs = st.executeQuery("SELECT * FROM doctordetails WHERE Specialisation = '"+cmbxSpecialisation.getSelectedItem()+"'");
                            
                                //while loop declaration
                                while(rs.next()){
                                    //adding existing doctors that are to that specific specailisation
                                    //displaying doctor name and id, in the combo box
                                    cmbxDoctors.addItem(rs.getString("DoctorName")+" ("+rs.getString("DoctorID")+")");
                                }
                            
                            } catch (SQLException ex) {
                                Logger.getLogger(ex.getMessage());
                            }
                            //seting bounds of combo box
                            cmbxDoctors.setBounds(150, 220, 210, 30);
                            //adding combo box to frame
                            frmNewApp.getContentPane().add(cmbxDoctors);
                        }
                    });
                    
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ex.getMessage());
                }
                
                //label and textfield declaration, set bounds and adding to frame, uneditable (when required)
                JLabel lblDate = new JLabel("Date");
                lblDate.setBounds(20, 270, 150, 30);
                frmNewApp.getContentPane().add(lblDate);
                
                JTextField txtDate = new JTextField("yyyy-mm-dd");
                txtDate.setBounds(150, 270, 210, 30);
                frmNewApp.getContentPane().add(txtDate);
                
                JLabel lblTime = new JLabel("Time");
                lblTime.setBounds(20, 320, 150, 30);
                frmNewApp.getContentPane().add(lblTime);
                
                JTextField txtTime = new JTextField("hh:mm");
                txtTime.setBounds(150, 320, 210, 30);
                frmNewApp.getContentPane().add(txtTime);
                
                JLabel lblRoom = new JLabel("Room Number");
                lblRoom.setBounds(20, 370, 150, 30);
                frmNewApp.getContentPane().add(lblRoom);
                
                JTextField txtRoom = new JTextField();
                txtRoom.setBounds(150, 370, 210, 30);
                frmNewApp.getContentPane().add(txtRoom);
                
                JLabel lblStatus = new JLabel("Status");
                lblStatus.setBounds(20, 420, 150, 30);
                frmNewApp.getContentPane().add(lblStatus);
                
                JTextField txtStatus = new JTextField("Pending");
                txtStatus.setEditable(false);
                txtStatus.setBackground(Color.white);
                txtStatus.setBounds(150, 420, 210, 30);
                frmNewApp.getContentPane().add(txtStatus);
                
                JLabel lblSymptom = new JLabel("Symptoms");
                lblSymptom.setBounds(20, 470, 150, 30);
                frmNewApp.getContentPane().add(lblSymptom);
                
                JTextArea txtSymptoms = new JTextArea();
                txtSymptoms.setBounds(150, 470, 210, 70);
                frmNewApp.getContentPane().add(txtSymptoms);
                
                //button declaration
                JButton btnSaveAppointment = new JButton("Save");
                //button set bounds
                btnSaveAppointment.setBounds(260, 560, 100, 30);
                //adding button to frame
                frmNewApp.getContentPane().add(btnSaveAppointment);
                
                //action listener, when clicked
                btnSaveAppointment.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //declaring counter variable and initailising it to zero
                        int counter = 0;
                        //declaring string variable and initailising it to null
                        String PatientID = null;
                        
                        //checking if patient id textfield is empty
                        if(txtPatientID.equals("")){
                            //display message dialog
                            JOptionPane.showMessageDialog(null, "Patient ID can not be empty.", "Error!",JOptionPane.INFORMATION_MESSAGE);                              
                            //increment of counter
                            counter++;
                        }else{
                            //getting text from textfield
                            PatientID = txtPatientID.getText().toString();
                        }
                        
                        //string varibales declaration
                        String PatientName, PatientSurname;
                        //getting texts from textfields
                        PatientName = txtPatientName.getText().toString();
                        PatientSurname = txtPatientSurname.getText().toString();
                        
                        //getting selected item rom combo box
                        String selectedDoc = cmbxDoctors.getSelectedItem().toString();
                        //geting doctor id, from combo box, using substring
                        String DoctorID = selectedDoc.substring(selectedDoc.indexOf("(") + 1, selectedDoc.indexOf(")"));
                        //s.substring(int start, int end);
                        
                        //string variables declared and initialised to null
                        String DoctorName=null, DoctorSurname=null;
                        
                        //try...catch declaration
                        try {
                            //resultset, execute query, where doctor id exists
                            rs = st.executeQuery("SELECT * FROM doctordetails WHERE DoctorID = '"+DoctorID+"'");
                            
                            //while loop declaration
                            while(rs.next()){
                                //display doctor information, if exists
                                DoctorName = rs.getString("DoctorName");
                                DoctorSurname = rs.getString("DoctorSurname");
                            }
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(ex.getMessage());
                        }
                        
                        //regular expression to check if dat is in the corretc format
                        String regexDate = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
                        //string date set to null
                        String appDate = null;
                            //if date format matches the regex
                            if(txtDate.getText().toString().matches(regexDate)){
                                //initialise variable with string from textfield
                                appDate = txtDate.getText().toString();
                            }else{
                                //display if date format is incorrect
                                JOptionPane.showMessageDialog(null, "Incorrect date format.", "Error!",JOptionPane.INFORMATION_MESSAGE);  
                                counter++;
                            }
                        
                        //regular expression  for time   
                        String regexTime = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
                        //setting time variable to null
                        String appTime=null;
                        //if time user input matches regex
                        if((txtTime.getText().toString()).matches(regexTime)){
                            //setting time from textfield 
                            appTime = txtTime.getText().toString();
                        }else{
                            //display in message dialog
                            JOptionPane.showMessageDialog(null, "Time format is incorrect.", "Error!",JOptionPane.INFORMATION_MESSAGE);                              
                            counter++;
                        }
                        
                        //regex for numbers only
                        String regexRoom = "^[0-9]+"; 
                        //setting room number as null
                        String RoomNumber=null;
                        //checking if user input matches regex
                        if((txtRoom.getText().toString()).matches(regexRoom)){
                            //setting variable with textfield text
                            RoomNumber = txtRoom.getText().toString();
                        }else{
                            //show message dialog, if error
                            JOptionPane.showMessageDialog(null, "Room number should not contain letters.", "Error!",JOptionPane.INFORMATION_MESSAGE);                              
                        }
                        
                        //getting text from radio button
                        String Status = txtStatus.getText().toString();
                        
                        //regex for letters only
                        String regexSymptom = "^[a-zA-Z\\s]*$"; 
                        //setting variable to null
                        String Symptoms=null;
                        //checking if text matches regex
                        if((txtSymptoms.getText()).toString().matches(regexSymptom)){
                            //set variable to equal text
                            Symptoms = txtSymptoms.getText().toString();
                        }else{
                            //show message dialog, if error
                            JOptionPane.showMessageDialog(null, "Symptoms should only contain letters", "Error!",JOptionPane.INFORMATION_MESSAGE);                              
                            //increment of counter
                            counter++;
                        }
                        
                        
                               try {
                            //resultset, execute query, where doctor id exists
                            rs = st.executeQuery("SELECT * FROM appointmentdetails WHERE DoctorID = '"+DoctorID+"'");
                            
                            //while loop declaration
                            while(rs.next()){
                                //checking if Doctor is available on the date
                                if(rs.getString("Date").equals(appDate) && rs.getString("DoctorID").equals(DoctorID)){
                                
                                    counter++;
                                    
                                    JOptionPane.showMessageDialog(null, "Doctor is already booked", "Error!",JOptionPane.INFORMATION_MESSAGE);  
                                    
                                }
                                
                            }
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(ex.getMessage());
                        }
                               
                               
                        //if counter is zero, meaning no errors
                        if(counter==0){
                            try {
                                //insert statement execute
                                st.executeUpdate("INSERT INTO appointmentdetails(PatientName, PatientSurname, PatientID, DoctorName, DoctorSurname, DoctorID, Date, Time, RoomNumber, Status, Symptoms, Diagnosis, Treatment) VALUES('"+PatientName+"','"+PatientSurname+"','"+PatientID+"','"+DoctorName+"','"+DoctorSurname+"','"+DoctorID+"','"+appDate+"','"+appTime+"','"+RoomNumber+"','"+Status+"','"+Symptoms+"','Pending','Pending')");     
                                //message dialog, when successful
                                JOptionPane.showMessageDialog(null, "Appointment Created.", "Error!",JOptionPane.INFORMATION_MESSAGE);                 
                                //disposing of frame
                                frmNewApp.dispose();
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error!",JOptionPane.INFORMATION_MESSAGE);
                            }
                        }else{
                            //errors exist, error is displayed
                            JOptionPane.showMessageDialog(null, "Not successful.", "Error!",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });
                
            }
            
        });
        
        //button to display all existing patients which are registered
        btnPatients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //try...catch execution
                try {
                    //resultset, execute query
                    rs = st.executeQuery("select PatientID, PatientName, PatientSurname, PatientGender, PatientPhoneNumber from patientdetails");
                    //use resultset to display in jtable
                    tbGeneral.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException ex) {
                    Logger.getLogger(ex.getMessage());
                }
            }
        });
        
        //button to update treatment
        btnUpdateTreatment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //getting table row
                int row = tbGeneral.getSelectedRow();
                
                //checking if table is correct, to update treatment
                if(tbGeneral.getColumnCount()<6){
                    JOptionPane.showMessageDialog(null, "Incorrect table selected.", "Error!",JOptionPane.INFORMATION_MESSAGE);          
                }else{
                
                //checking if row is selected
                if(row<0){
                   JOptionPane.showMessageDialog(null, "No row selected.", "Error!",JOptionPane.INFORMATION_MESSAGE);  
                }else{
                
                //getting value at row 2, paient ID    
                String tbVal = (tbGeneral.getValueAt(row, 2)).toString();
                
                //try...catch declaration
                try {
                    //resultset, execute query, where patient id exists
                    rs = st.executeQuery("SELECT * FROM patientdetails WHERE PatientID = '"+tbVal+"'");
                    //while loop declaration
                    while(rs.next()){
                        
                        //frame declaration
                        JFrame frmTreatment = new JFrame("Update Treatment");
                        //setting frame properties
                        frmTreatment.setVisible(true);
                        frmTreatment.setLayout(null);
                        frmTreatment.setSize(300, 450);
                        frmTreatment.setLocation(500,200);
                        frmTreatment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        //label declaration
                        JLabel lblPName = new JLabel();
                        //setting label properties
                        lblPName.setText("Patient Name");
                        lblPName.setBounds(10, 20, 100, 30);
                        //adding label to frame
                        frmTreatment.getContentPane().add(lblPName);
                        
                        //textfield declaration
                        JTextField txtPatientName = new JTextField();
                        //setting textfield properties
                        txtPatientName.setText(rs.getString("PatientName"));
                        txtPatientName.setBounds(120, 20, 150, 30);
                        txtPatientName.setBackground(Color.white);
                        txtPatientName.setEditable(false);
                        //adding textfield to frame
                        frmTreatment.getContentPane().add(txtPatientName);
                        
                        JLabel lblPSurname = new JLabel();
                        lblPSurname.setText("Patient Surname");
                        lblPSurname.setBounds(10, 70, 100, 30);
                        frmTreatment.getContentPane().add(lblPSurname);
                        
                        JTextField txtPatientSurname = new JTextField();
                        txtPatientSurname.setText(rs.getString("PatientSurname"));
                        txtPatientSurname.setBounds(120, 70, 150, 30);
                        txtPatientSurname.setBackground(Color.white);
                        txtPatientSurname.setEditable(false);
                        frmTreatment.getContentPane().add(txtPatientSurname);
                        
                        JLabel lblDiagnosis = new JLabel();
                        lblDiagnosis.setText("Diagnosis");
                        lblDiagnosis.setBounds(10, 130, 100, 30);
                        frmTreatment.getContentPane().add(lblDiagnosis);
                        
                        //declaration of a new resultset
                        //executing query where patient id exists
                        ResultSet rs2 = st.executeQuery("SELECT * FROM appointmentdetails WHERE PatientID = '"+tbVal+"'");
                        
                        //while loop declaration
                        while(rs2.next()){
                           JTextArea txtaDiagnosis = new JTextArea();
                            txtaDiagnosis.setText(rs2.getString("Diagnosis"));
                            txtaDiagnosis.setBounds(120, 130, 150, 80);
                            txtaDiagnosis.setBackground(Color.white);
                            txtaDiagnosis.setEditable(false);
                            frmTreatment.getContentPane().add(txtaDiagnosis);

                            JLabel lblTreatment = new JLabel();
                            lblTreatment.setText("Treatment");
                            lblTreatment.setBounds(10, 230, 100, 30);
                            frmTreatment.getContentPane().add(lblTreatment);

                            JTextArea txtaTreatment = new JTextArea();
                            txtaTreatment.setBounds(120, 230, 150, 80);
                            frmTreatment.getContentPane().add(txtaTreatment);

                            //button declaration
                            JButton btnSave = new JButton("Save");
                            btnSave.setBounds(170, 350, 100, 30);
                            frmTreatment.getContentPane().add(btnSave);

                            //button action listener to save treatment update
                            btnSave.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    //getting text from textfield
                                    String Treatment = txtaTreatment.getText().toString();
                                    //try...catch declaration
                                    try {
                                        //executation of update statement, treatment column where patient exists
                                        st.execute("UPDATE appointmentdetails SET Treatment = '"+Treatment+"' WHERE PatientID = '"+tbVal+"'");
                                        //dispose of treatment frame when successful
                                        frmTreatment.dispose();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(ex.getMessage());
                                    }

                                }
                            }); 
                        }
                        
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ex.getMessage());
                }
                
            }
           }
           }
        });
               
        //button to save registeration of patient
        btnRegisterPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //frame declaration
                JFrame frmReg = new JFrame("Patient Registration");
                //setting frame properties
                frmReg.setVisible(true);
                frmReg.setLayout(null);
                frmReg.setSize(400,400);
                frmReg.setLocation(500,180);
                frmReg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                //label declaration, setting property and adding to frame
                JLabel lblPatientID = new JLabel("Patient ID");
                lblPatientID.setBounds(20, 20, 150, 30);
                frmReg.getContentPane().add(lblPatientID);
                
                //textfield declaration, setting property and adding to frame
                JTextField txtPatientID = new JTextField();
                txtPatientID.setBounds(150, 20, 210, 30);
                frmReg.getContentPane().add(txtPatientID);
                
                //label declaration, setting property and adding to frame
                JLabel lblPatientName = new JLabel("Patient Name");
                lblPatientName.setBounds(20, 60, 150, 30);
                frmReg.getContentPane().add(lblPatientName);
                
                //textfield declaration, setting property and adding to frame
                JTextField txtPatientName= new JTextField();
                txtPatientName.setBounds(150, 60, 210, 30);
                frmReg.getContentPane().add(txtPatientName);
                
                //label declaration, setting property and adding to frame
                JLabel lblPatientSurname = new JLabel("Patient Surname");
                lblPatientSurname.setBounds(20, 100, 150, 30);
                frmReg.getContentPane().add(lblPatientSurname);
                
                //textfield declaration, setting property and adding to frame
                JTextField txtPatientSurname = new JTextField();
                txtPatientSurname.setBounds(150, 100, 210, 30);
                frmReg.getContentPane().add(txtPatientSurname);
                
                //label declaration, setting property and adding to frame
                JLabel lblPassword = new JLabel("Patient Password");
                lblPassword.setBounds(20, 140, 150, 30);
                frmReg.getContentPane().add(lblPassword);
                
                //textfield declaration, setting property and adding to frame
                JTextField txtPassword = new JTextField();
                txtPassword.setBounds(150, 140, 210, 30);
                frmReg.getContentPane().add(txtPassword);
                
                //label declaration, setting property and adding to frame
                JLabel lblGender = new JLabel("Patient Gender");
                lblGender.setBounds(20, 180, 150, 30);
                frmReg.getContentPane().add(lblGender);
                
                //radio button declaration and setting property
                JRadioButton rbMale = new JRadioButton("Male");
                JRadioButton rbFemale = new JRadioButton("Female");
                rbMale.setBounds(150, 180, 150, 30);
                rbFemale.setBounds(150, 200, 150, 30);
                        
                //button group declaration
                ButtonGroup bgStatus = new ButtonGroup();
                //adding radio button to radio group
                bgStatus.add(rbMale);
                bgStatus.add(rbFemale);
                //adding radio buttons to frame
                frmReg.add(rbMale);
                frmReg.add(rbFemale);
                
                JLabel lblPhoneNum = new JLabel("Phone Number");
                lblPhoneNum.setBounds(20, 240, 150, 30);
                frmReg.getContentPane().add(lblPhoneNum);
                
                JTextField txtPhoneNum = new JTextField();
                txtPhoneNum.setBounds(150, 240, 210, 30);
                frmReg.getContentPane().add(txtPhoneNum);
                
                //button declaration, setting property and adding to frame
                JButton btnSaveRec = new JButton("Save");
                btnSaveRec.setBounds(260, 300, 100, 30);
                frmReg.getContentPane().add(btnSaveRec);
                
                //action listener for saving new patient rec
                btnSaveRec.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        int counter = 0;
                        
                        //regex for numbers only
                        String regexID = "[0-9]{7}";
                        //setting patient id to null
                        String PatientID = null;
                        //if text matches regex and length
                        if((txtPatientID.getText().toString().matches(regexID))){
                            //try...catch declaration
                            try {
                                //resultset, execute query
                                rs = st.executeQuery("SELECT * FROM patientdetails");
                                
                                //while loop declaration
                                while(rs.next()){
                                    //checking if patient id already exists
                                    if(rs.getString("PatientID").equals(txtPatientID.getText().toString())){
                                        //display that patient exists
                                        counter++;
                                        JOptionPane.showMessageDialog(null, "Patient Already Exists.", "Error!",JOptionPane.INFORMATION_MESSAGE);  
                                        break;
                                    }else{
                                        //set patient id with textfield value
                                        PatientID = txtPatientID.getText().toString();
                                    }
                                }
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(QuestionOne.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }else{
                            counter++;
                        }
                        
                        //regex for letters only
                        String regexName = "^[a-zA-Z]*$";
                        //setting variable to null
                        String PatientName = null;
                        //checking if  text matches regex
                        if(txtPatientName.getText().toString().matches(regexName)){
                            //setting variable with textfield text
                            PatientName = txtPatientName.getText().toString();
                        }else{
                            counter++;
                            //display if incorrect format
                            JOptionPane.showMessageDialog(null, "Incorrect format.", "Error!",JOptionPane.INFORMATION_MESSAGE);  
                        }
                        
                        //regex for letters only
                        String regexSName = "^[a-zA-Z]*$";
                        //setting variable to null
                        String PatientSurname = null;
                        //checking if  text matches regex
                        if(txtPatientSurname.getText().toString().matches(regexSName)){
                            //setting variable with textfield text
                            PatientSurname = txtPatientSurname.getText().toString();
                        }else{
                            counter++;
                            //display if incorrect format
                            JOptionPane.showMessageDialog(null, "Incorrect format.", "Error!",JOptionPane.INFORMATION_MESSAGE);  
                        }
                        
                        //setting string variable to null
                        String Password = null;
                        //checking if textfield is empty
                        if(txtPassword.getText().equals("")){
                            JOptionPane.showMessageDialog(null, "Password can not be blank.", "Error!",JOptionPane.INFORMATION_MESSAGE);  
                        }else{
                            //regex, password has to be 8 charactrs or more
                            String regexPass = ".{8,}";
                            //checking if text matches regex
                            if(txtPassword.getText().toString().matches(regexPass)){
                                //setting variable with text
                                Password = txtPassword.getText().toString();
                            }else{
                                counter++;
                                JOptionPane.showMessageDialog(null, "Password should be 8 or more characters.", "Error!",JOptionPane.INFORMATION_MESSAGE);  
                            }
                        }
                        
                        //regex for numbers only, with length of 10
                        String regexPNum= "^\\d{10}$";
                        //settinf string to null
                        String PatientPNum = null;
                        //checking if text matches regex
                        if(txtPhoneNum.getText().toString().matches(regexPNum)){
                            //setting varible with textfield text
                            PatientPNum = txtPhoneNum.getText().toString();
                        }else{
                            counter++;
                            //display incorrect format
                            JOptionPane.showMessageDialog(null, "Incorrect format.", "Error!",JOptionPane.INFORMATION_MESSAGE);  
                            
                        }
                        
                        //string variable set to empty
                        String PatientGen = "";
                        //if first radio group is selected
                        if(rbMale.isSelected()){
                            //set varaible value to text
                            PatientGen = "Male";
                        }
                        //if first radio group is selected
                        else if(rbFemale.isSelected()){
                            //set varaible value to text
                            PatientGen = "Female";
                        }
                     
                        try {
                            //insert into patients table, in database,if no errors
                            if(counter==0){
                                st.execute("INSERT INTO patientdetails VALUES ('"+PatientID+"','"+PatientName+"','"+PatientSurname+"','"+Password+"','"+PatientGen+"','"+PatientPNum+"')");      
                                JOptionPane.showMessageDialog(null, "Patient registered.", "Successful",JOptionPane.INFORMATION_MESSAGE);  
                            }else{
                                JOptionPane.showMessageDialog(null, "Registration failed.", "Error!",JOptionPane.INFORMATION_MESSAGE);  
                            }
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(ex.getMessage());
                        }
                        
                    }
                });
                
            }
        });
        
        JButton btnLogOut = new JButton("Log Out");
        btnLogOut.setBounds(660, 630, 150, 30);
        frmNurse.getContentPane().add(btnLogOut);
        
        //action listener for logout
        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //calling login method
                Login();
                //dispose nurse frame
                frmNurse.dispose();
            }
        });
        
    }
    
    public static void Patient(String id){
        
        //frame declaration
        JFrame frmPatient = new JFrame("Patient Access");
        //setting frame properties
        frmPatient.setVisible(true);
        frmPatient.setLayout(null);
        frmPatient.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frmPatient.setLocation(0,0);
        frmPatient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //label declaration, set properties and adding to frame
        JLabel lblPatientID = new JLabel("Patient ID");
        lblPatientID.setBounds(20, 30, 150, 30);
        frmPatient.getContentPane().add(lblPatientID);

        //textfield declaration, set properties and adding to frame
        JTextField txtPatientID = new JTextField();
        txtPatientID.setEditable(false);
        txtPatientID.setBackground(Color.white);
        txtPatientID.setBounds(150, 30, 210, 30);
        frmPatient.getContentPane().add(txtPatientID);

        JLabel lblPatientName = new JLabel("Patient Name");
        lblPatientName.setBounds(20, 110, 150, 30);
        frmPatient.getContentPane().add(lblPatientName);

        JTextField txtPatientName= new JTextField();
        txtPatientName.setEditable(false);
        txtPatientName.setBackground(Color.white);
        txtPatientName.setBounds(150, 110, 210, 30);
        frmPatient.getContentPane().add(txtPatientName);

        JLabel lblPatientSurname = new JLabel("Patient Surname");
        lblPatientSurname.setBounds(20, 190, 150, 30);
        frmPatient.getContentPane().add(lblPatientSurname);

        JTextField txtPatientSurname = new JTextField();
        txtPatientSurname.setEditable(false);
        txtPatientSurname.setBackground(Color.white);
        txtPatientSurname.setBounds(150, 190, 210, 30);
        frmPatient.getContentPane().add(txtPatientSurname);

        JLabel lblPassword = new JLabel("Patient Password");
        lblPassword.setBounds(20, 270, 150, 30);
        frmPatient.getContentPane().add(lblPassword);

        JTextField txtPassword = new JTextField();
        txtPassword.setEditable(false);
        txtPassword.setBackground(Color.white);
        txtPassword.setBounds(150, 270, 210, 30);
        frmPatient.getContentPane().add(txtPassword);

        JLabel lblGender = new JLabel("Patient Gender");
        lblGender.setBounds(20, 350, 150, 30);
        frmPatient.getContentPane().add(lblGender);

        JTextField txtGender = new JTextField();
        txtGender.setEditable(false);
        txtGender.setBackground(Color.white);
        txtGender.setBounds(150, 350, 210, 30);
        frmPatient.getContentPane().add(txtGender);

        JLabel lblPhoneNum = new JLabel("Phone Number");
        lblPhoneNum.setBounds(20, 430, 150, 30);
        frmPatient.getContentPane().add(lblPhoneNum);

        JTextField txtPhoneNum = new JTextField();
        txtPhoneNum.setEditable(false);
        txtPhoneNum.setBackground(Color.white);
        txtPhoneNum.setBounds(150, 430, 210, 30);
        frmPatient.getContentPane().add(txtPhoneNum); 
        
        try {
            //resultset, execute query
            rs = st.executeQuery("SELECT * FROM patientdetails WHERE PatientID = '"+id+"'");
            
            //while loop declaration
            while(rs.next()){
                //displaying patiint details
                txtPatientID.setText(rs.getString("PatientID"));
                txtPatientName.setText(rs.getString("PatientName"));
                txtPatientSurname.setText(rs.getString("PatientSurname"));
                txtPassword.setText(rs.getString("PatientPassword"));
                txtGender.setText(rs.getString("PatientGender"));
                txtPhoneNum.setText(rs.getString("PatientPhoneNumber")); 
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error!",JOptionPane.INFORMATION_MESSAGE);
        }
        
        //button declaration, set properties and adding to frame
        JButton btnAppointments = new JButton("Appointments");
        btnAppointments.setBounds(600, 30, 200, 30);
        frmPatient.getContentPane().add(btnAppointments);
        
        JButton btnMedHist = new JButton("Medical History");
        btnMedHist.setBounds(900, 30, 200, 30);
        frmPatient.getContentPane().add(btnMedHist);
        
        //jtable and scrollpane declaration, set properties and adding to frame
        JTable tbGeneral = new JTable();
        JScrollPane srcpn = new JScrollPane(tbGeneral);
        srcpn.setBounds(400, 80, 900, 500);
        frmPatient.getContentPane().add(srcpn);
        
        //button action listener
        btnAppointments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //resultset, executing query where patient id exists
                    rs = st.executeQuery("SELECT PatientID, PatientName, PatientSurname, DoctorName, DoctorSurname, Date, Time, RoomNumber, Symptoms FROM appointmentdetails WHERE PatientID = '"+id+"' AND Status = 'Pending'");
                    //adding rows to required table
                    tbGeneral.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error!",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        btnMedHist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //resultset, executing query where patient id exists
                    rs = st.executeQuery("SELECT DoctorName, DoctorSurname, Date, Time, RoomNumber, Symptoms, Diagnosis, Treatment FROM appointmentdetails WHERE PatientID = '"+id+"' AND Status = 'Seen'");
                    //adding rows to required table
                    tbGeneral.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error!",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        JButton btnLogOut = new JButton("Log Out");
        btnLogOut.setBounds(660, 630, 150, 30);
        frmPatient.getContentPane().add(btnLogOut);
        
        //logout action listener
        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ccaling loging function
                Login();
                //disposing of patient frame
                frmPatient.dispose();
            }
        });
        
    }
    
    public static void main(String[] args) {  
        //calling Login method
        Login();   
        //calling Database connection method to make sure the connection exists for statements to work
        DatabaseCon();
    }
    
}
