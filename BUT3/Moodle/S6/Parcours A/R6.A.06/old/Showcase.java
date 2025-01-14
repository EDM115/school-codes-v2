public class Showcase extends Applet implements EventOutObserver {
  //Globals
  String homeUrl="http://www.webserver.com /images/" ;
  int caseState;
  String url="jdbc:odbc:WebApp";
  Driver theDriver;
  Connection con=null;
  ResultSet rs,counter;
  int theLevel;
  int count=0;
  String tino;
  //lignes 17-32
  int [] clickx;
  int [] clicky;
  String [] actions;
  String [] images;
  String [] spectra;
  String showcaseQuery=null;
  TextArea output=null;
  Browser browser=null;
  Node material=null;
  EventInSFColor diffuseColor=null;
  EventOutSFColor outputColor=null;
  EventOutSFTime touchTime=null;
  boolean error=false;
  EventInMFNode addChildren;
  Node mainGroup=null;
  EventOutSFVec2f coord=null;
  //lignes 33-80
  EventInSFVec3f translation=null;
  EventOutSFTime theClick=null;
  Image test;
  int rx,ry;
  float arx,ary;
  int b=0;
  Graphics gg=null;
  
  //Initialize applet
  public void init() {
    super.init();
    setLayout(null);
    initMenus();
    output=new TextArea(5, 40);
    add(output);
    browser=(Browser) Browser.getBrowser((Applet)this);
    addNotify();   
    resize(920,800);
    initUndoStack();
    caseState=0;
    theLevel=0;
    setClock(0);
    try {
      theDriver=new postgresql.Driver();
    } catch(Exception e) {}
    try {
      con=DriverManager.getConnection( "jdbc:postgresql://www.webserver. .com/WebApps", "postgres","");
      Statement stmt=con.createStatement(); 
      showcaseQuery="SELECT sid, case, button,text , name, actions FROM WebApp WHERE case="+caseState+
        " and level="+theLevel+";";
      rs=stmt.executeQuery (showcaseQuery);
      count=0;
      while (rs.next()) count++;
      System.out.println("Count= "+count+"\n");
      rs=stmt.executeQuery (showcaseQuery);
    }
    catch(Exception e) {
      System.out .println( "Error connecting and running: "+e);    //lignes 81_128
    }
    
    nextButton=new symantec.itools.awt.ImageButton();
    lastButton=new symantec.itools.awt.ImageButton();
    try { 
      nextButton.setImageURL(new java.net.URL( "http://www.webserver.com:8080/ images/next.jpg"));
      if (count<7) nextButton .setVisible(false);
      else nextButton.setVisible (true);
      lastButton.setImageURL(new java.net.URL( "http://www.webserver.com:8080/ images/last.jpg"));
    } catch(Exception e) {}
    imageButtons=new symantec.itools.awt.ImageButton[6];
    l1=new symantec.itools.awt.shape.Horizontal Line();
    l2=new symantec.itools.awt.shape.Horizontal Line();
    v1=new symantec.itools.awt.shape.Vertical Line();
    v2=new symantec.itools.awt.shape.Vertical Line();
    bigspectralabel=new java.awt.Label("Spectra");
    gtruthlabel=new java.awt.Label("GroundTruth");
    clickx=new int[6];
    clicky=new int[6];
    actions=new String[6];
    images=new String[6];
    spectra=new String[6];
    imageLabels =new java.awt.Label[6]; 
    for (int I=0; i<6 ; i++) {                // lignes 129-176
      imageButtons[i]=new symantec.itools.awt.ImageButton();
      imageLabels[i]=new java.awt .Label();
      actions[i]=new String();
      images[i] =new String();
      spectra[i] =new String();
    }
    for (int i=0; i<6 ; i++) {
      try{
        rs.next();
        tino=rs.getString(4);
        System.out.println(tino+"\n");
        actions[i]=rs.getString(6);
      } catch(Exception e) {System.out .println("SQL Error :"+e);}
      try{
        System.out.print(tino+ln"\n");
        int len=tino.length();
        if (tino.startsWith ("INVISIBLE")) {
          imageButtons[i].setVisible(false);
          imageLabels[i] 
            .setVisible(false);
        } else { 
          imageButtons[i].setImageURL( new java.net.URL (homeUrl+tino));
          imageButtons[i].setVisible (true);
          imageLabels[i].setText (rs.getString(5));
          imageLabels[i].setVisible (true);
        } 
      } catch (Exception e) {
        System.out .println( "Died in accessor statement: "+e);
      }
    }
    l1.reshape(0,6,775,1);
    add(l1);
    l2.reshape(0,120,775,1);
    add(12);
    v1.reshape(0,6,1,114);
    add(v1);
    v2.reshape(775,6,1,114);
    add(v2); 
    //LINES_177–224
    bigspectralabel.reshape (460,122,200,16);
    bigspectralabel.setVisible(false);
    gtruthlabel.reshape(124,122,200,16);
    gtruthlabel.setVisible(false);
    add(bigspectralabel);
    add (gtruthlabel);
    nextButton.reshape(2,12,84,40);
    add(nextButton);
    lastButton.reshape (2,56,84,40);
    add(lastButton);
    imageLabels[0].reshape(124, 12,84,16);
    add(imageLabels[0]);
    imageButtons[0].reshape (124,30 ,84,84);
    add(imageButtons[0]);
    imageLabels[1].reshape (236,12,84,16);
    add(imageLabels[1]);
    imageButtons[1].reshape (236,30,84,84);
    add(imageButtons[1]);
    imageLabels[2].reshape (348,12,84,16);
    add(imageLabels[2]);
    imageButtons[2].reshape (348,30 ,84,84);
    add(imageButtons[2]);
    imageLabels[3].reshape (460,12,84,16);
    add(imageLabels[3]);
    imageButtons[3].reshape (460,30 ,84,84);
    add(imageButtons[3]);
    imageLabels[4].reshape (572,12,84,16);
    add(imageLabels[4]);
    imageButtons[4].reshape (572,30 ,84,84);
    add(imageButtons[4]);
    imageLabels[5].reshape (684,12,84,16);
    add(imageLabels[5]); 
    imageButtons[5].reshape (684,30 ,84,84);
    add(imageButtons[5]);
    // Take out this line if you don’t use 
    // symantec.itools.net.RelativeURL
    symantec.itools.lang.Context.setDocumentBase(getDocumentBase());
    //{{INIT_CONTROLS
    //}}
  }
}
  