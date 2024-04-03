//TODO 193行 目前進度 : 改寫完MVC架構 寫好新增   現在要改好 查

import java.security.SecureRandom;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.Exception;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.geometry.Insets;

public class ScanTheMemorizeMain extends Application{
  String nameList = "nameList";

  Stage mStageMain;
  Scene mSceneMain;

  //查人用
  TextField mTxfLookUp = new TextField("檢索...");

  //獨一無二的身分認證
  Label mLblIndex = new Label("通行識別碼 : ");
  TextField mTxfIndex = new TextField("");

  //真實世界的姓名
  Label mLblRealId = new Label("真實ID : ");
  TextField mTxfRealId = new TextField("");

  //認識的原因
  Label mLblFirstMeet = new Label("初識地點 : ");
  TextField mTxfFirstMeet = new TextField("");

  //%% time !
  Label mLblPlayTime = new Label("可以澀澀的時間♂♂");
  Button[][] mBtnMtx = new Button[4][8];
  boolean[][] mBolMtx = new boolean[4][8];

  //紀錄這個團友的基礎資料
  Label mLblLabel = new Label("標籤");
  Button[] mBtnAryLabels = new Button[10];

  //g3為"史"
  Label mLblHistory = new Label("g3:");
  TextArea mTxaHistory = new TextArea("");

  //顯示查詢的項目
  Label mLblResult = new Label("");

  //新建檔按鈕
  Button mBtnCreate = new Button("新建檔案");
  //儲存按鈕
  Button mBtnUpdate = new Button("更新");

  //一些顏色常數
  BackgroundFill orangeFill = new BackgroundFill(Paint.valueOf("#FF8000"), new CornerRadii(30), Insets.EMPTY);
  BackgroundFill greenFill = new BackgroundFill(Paint.valueOf("#82D900"), new CornerRadii(10), Insets.EMPTY);
  BackgroundFill whiteFill = new BackgroundFill(Paint.valueOf("#FFFFFF"), new CornerRadii(10), Insets.EMPTY);
  BackgroundFill blueFill = new BackgroundFill(Paint.valueOf("#0080FF"), new CornerRadii(5), Insets.EMPTY);
  // BackgroundFill redFill = new BackgroundFill(Paint.valueOf("#FF0000"), new CornerRadii(90), Insets.EMPTY);
  // BackgroundFill blackFill = new BackgroundFill(Paint.valueOf("#000000"), new CornerRadii(90), Insets.EMPTY);

  Background orange = new Background(orangeFill);
  Background green = new Background(greenFill);
  Background white = new Background(whiteFill);
  Background blue = new Background(blueFill);
  // Background red = new Background(redFill);
  // Background black = new Background(blackFill);

  public void start(Stage primaryStage){
    //初始化布局
    initialLayout();

    //設定時間表按鈕事件
    lightColorSwitchEvent(mBtnMtx[1], mBolMtx[1]);
    lightColorSwitchEvent(mBtnMtx[2], mBolMtx[2]);
    lightColorSwitchEvent(mBtnMtx[3], mBolMtx[3]);

    //妥善查詢功能
    lookUpSetting(mTxfLookUp);

    //妥善新建功能
    createSetting(mBtnCreate);

    //妥善更新功能  //TODO 未處理
    updateSetting(mBtnUpdate);

    mStageMain.show();
  }

  //妥善查詢功能
  public void lookUpSetting(TextField mTxf){
    final Alert alertEmpty = new Alert(AlertType.WARNING);
    alertEmpty.setTitle("找不到");
    alertEmpty.setHeaderText("尚未建立任何資料");
    alertEmpty.setContentText("資料庫為0ㄛ\n沒資料找啥呢老哥");

    final Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("hk");
    alert.setHeaderText("標題");
    alert.setContentText("內文");

    File fileDb = new File(nameList);
    mTxf.setOnAction(e->{
      try{
        //當 DB為不存在 或是DB不是資料夾時
        if(!fileDb.exists() || !fileDb.isDirectory()) alertEmpty.show();
        else{
          // String[] mStrAryData = fileDb.list();
          //matchNumber 計算有幾個結果符合查詢
          int matchNumber = 0;
          String targetFileName = "";
          //for-each 巡視資料夾內所有檔案名稱
          for(String str : fileDb.list()){
            //若有檔案名稱包含搜尋框的文字
            if(str.contains(mTxf.getText() ) ){
              //TODO 寫活//結果+1
              matchNumber++;
              targetFileName = str;
            }
          }
          //若尋找到的結果恰好為1 //TODO 要改為可被選擇 很後期的TODO
          if(matchNumber == 1){
            File personWhoLookingFor = new File(nameList + "/" + targetFileName);
            //依據尋找的結果填入
            //fillDataFromResult(personWhoLookingFor);
            alert.showAndWait();
          }
        }
        throw new IOException();
      }catch(IOException ioex){
        ioex.printStackTrace();
      }catch(Exception ex){
        ex.printStackTrace();
      }

    }); //EVENT END

  }

  //依據尋找的結果填入
  public void fillDataFromResult(File file) throws IOException{
    Profile pr = new Profile(file);
    mLblResult.setText("現正查詢: "+file.getName());
    mTxfIndex.setText(pr.getPersonIndex());
    mTxfRealId.setText(pr.getName());
    mTxfFirstMeet.setText(pr.getFirstPlace());
    // spreadOutFreeTime(pr.getFreeTime(), mBolMtx);  //TODO
    mTxaHistory.setText(pr.getHistory());
  }

  //妥善新建功能
  public void createSetting(Button mBtnCreate){
    final Alert alertCrash = new Alert(AlertType.WARNING);
    alertCrash.setTitle("Crash");
    alertCrash.setHeaderText("資料重複");
    alertCrash.setContentText("當前資料與現有的資料重複");

    final Alert alertSuccess = new Alert(AlertType.INFORMATION);
    alertSuccess.setTitle("Success");
    alertSuccess.setHeaderText("成功");
    alertSuccess.setContentText("資料已寫入");

    //新建資料按鈕 先檢查資料有無到齊 接著查看有沒有重複ID 都沒問題就登錄
    mBtnCreate.setOnAction(e->{
      try{
        //若資料齊且資料不與現有衝突就會設為true
        boolean createOk = false;

        //檢查資料有無到齊，此兩欄位皆有值則視為到齊
        createOk = mTxfIndex.getText().length() * mTxfRealId.getText().length() > 0;

        //摘錄介面上的資訊
        String index = mTxfIndex.getText();
        String realId = mTxfRealId.getText();
        String firstMeet = mTxfFirstMeet.getText();
        boolean[] freeTime = fetchFreeTime(); //TODO
        String history = mTxaHistory.getText();

        //登錄資料
        boolean state = false;
        if(createOk){
          Profile prNew = new Profile(index, realId, firstMeet, freeTime, history);
          state = prNew.createFile();
        }
        if(state) alertSuccess.show();
        else alertCrash.show();
      }catch(IOException ioex){
        System.out.print("寫檔這邊出了IOEX");
        ioex.printStackTrace();
      }catch(Exception ex){
        System.out.print("寫檔這邊有EX");
        ex.printStackTrace();
      }

    });
  }

  // 取得日程表資料
  private boolean[] fetchFreeTime(){
    boolean[] answer = new boolean[21];
    int temp = 0;
    for(int i = 0; i < mBolMtx.length; i++){
      if(i == 0) continue;
      //explain : -1 because answer don't need the last cell data
      for(int j = 0; j < mBolMtx[i].length-1; j++){
        answer[temp++] = mBolMtx[i][j];
      }
    }

    return answer;
  }

  //妥善更新功能
  public void updateSetting(Button mBtnUpdate){
    final Alert alertCrash = new Alert(AlertType.WARNING);
    alertCrash.setTitle("No result");
    alertCrash.setHeaderText("資料不重複");
    alertCrash.setContentText("當前資料不存在，請考慮[]新建資料]按鈕");

    final Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
    alertConfirm.setTitle("hk");
    alertConfirm.setHeaderText("");
    alertConfirm.setContentText("確定要更新資料嗎?");

    File fileDb = new File(nameList);

    mBtnUpdate.setOnAction(e->{
      try{
        //新檔案的名稱存在這邊
        String oldDataName = "";
        //若資料齊且資料符合現有所存在的資料就會設為true
        boolean updateOk = false;

        //檢查資料有無到齊，此兩欄位皆有值則視為到齊
        updateOk = mTxfIndex.getText().length() * mTxfRealId.getText().length() > 0;
        oldDataName = mTxfIndex.getText() + "_" + mTxfRealId.getText();


        //檢查有沒有重複ID
        //若資料夾不存在就建立一個資料夾
        if(updateOk && ( !fileDb.exists() || !fileDb.isDirectory() )) fileDb.mkdir();
        //先將可以新建檔案的狀態設為可以新建(假設當前資料與現有資料不重複)
        //for-each 巡視資料夾內所有檔案名稱
        for(String str : fileDb.list()){
          //若有檔案名稱與要新建的檔案標題 相同
          if(str.equals(oldDataName)){
            //將可以新建檔案的狀態更改為true
            updateOk = true;
            break;
          }
        }
        //顯示警告
        if(!updateOk) alertCrash.show();

        //登錄資料
        //寫檔案程式碼
        if(updateOk){
          // final Optional<ButtonType> opt = alertConfirm.showAndWait();
          alertConfirm.showAndWait();
          // final ButtonType rtn = opt.get(); //可以直接用「alert.getResult()」來取代
          final ButtonType mBty = alertConfirm.getResult();
          if(mBty == ButtonType.OK){
            FileWriter fileWritter = new FileWriter((nameList + "/" + oldDataName), false);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write("編號:" + mTxfIndex.getText() + "\r\n");
            bufferWritter.write("姓名:" + mTxfRealId.getText() + "\r\n");
            bufferWritter.write("初識:" + mTxfFirstMeet.getText() + "\r\n");
            // bufferWritter.write("時間表:" + mTxfIndex.getText() + "\r\n");
            bufferWritter.write("史料:\r\n");
            // bufferWritter.write("史料:" + mTxfIndex.getText() + "\r\n");
            bufferWritter.write("史料END\r\n");
            bufferWritter.close();
            fileWritter.close();
          }
        }
        throw new IOException();
      }catch(IOException ioex){
        System.out.print("更新檔案按鈕這邊出了IOEX");
        ioex.printStackTrace();
      }catch(Exception ex){
        System.out.print("更新檔案按鈕這邊有EX");
        ex.printStackTrace();
      }

    });
  }

  //陳設整理好的freeTime資料
  public void spreadOutFreeTime(boolean[] time, boolean[][] mBolMtx){
    for(int i = 1; i < mBolMtx.length; i++){
      for(int j = 0; j < mBolMtx[i].length-1; j++){
        //TODO 覺得寫得不好
        if(j*3+(i-1) > time.length) return;
        mBolMtx[i][j] = time[j*3+(i-1)];
      }
    }
    freshFreeTimeTable(mBolMtx);
  }

  //重整時間表
  public void freshFreeTimeTable(boolean[][] mBolMtx){
    for(int i = 1; i < mBtnMtx.length; i++){
      for(int j = 0; j < mBtnMtx[i].length-1; j++){
        mBtnMtx[i][j].setBackground( (mBolMtx[i][j])? green : white);
      }
    }
  }


  //initial layout setting
  public void initialLayout(){
    mLblIndex.setFont(Font.font("Amble CN", FontWeight.BOLD, 40));
    mLblRealId.setFont(Font.font("Amble CN", FontWeight.BOLD, 40));
    mLblFirstMeet.setFont(Font.font("Amble CN", FontWeight.BOLD, 40));
    mLblPlayTime.setFont(Font.font("Amble CN", FontWeight.BOLD, 40));
    mLblLabel.setFont(Font.font("Amble CN", FontWeight.BOLD, 40));
    mLblHistory.setFont(Font.font("Amble CN", FontWeight.BOLD, 40));
    mLblResult.setFont(Font.font("Amble CN", FontWeight.BOLD, 40));

    mTxfIndex.setFont(Font.font("Amble CN", FontWeight.BOLD, 40));
    mTxfIndex.setPrefWidth(300);
    mTxfIndex.setPrefHeight(30);
    mTxfRealId.setFont(Font.font("Amble CN", FontWeight.BOLD, 40));
    mTxfRealId.setPrefWidth(375);
    mTxfRealId.setPrefHeight(30);
    mTxfFirstMeet.setFont(Font.font("Amble CN", FontWeight.BOLD, 40));
    mTxfFirstMeet.setPrefWidth(339);
    mTxfFirstMeet.setPrefHeight(30);
    mTxaHistory.setFont(Font.font("Amble CN", FontWeight.BOLD, 40));
    mTxaHistory.setPrefWidth(700);
    mTxaHistory.setPrefHeight(300);

    mBtnCreate.setPrefWidth(160);
    mBtnCreate.setPrefHeight(60);
    mBtnCreate.setMinHeight(60);
    mBtnCreate.setBackground(blue);
    mBtnUpdate.setPrefWidth(160);
    mBtnUpdate.setPrefHeight(60);
    mBtnUpdate.setMinHeight(60);
    mBtnUpdate.setBackground(blue);



    HBox mHb0LookUp = new HBox(mTxfLookUp);
    mHb0LookUp.setSpacing(10);
    mHb0LookUp.setAlignment(Pos.CENTER);

    HBox mHbIndex = new HBox(mLblIndex, mTxfIndex);
    mHbIndex.setSpacing(10);
    mHbIndex.setAlignment(Pos.CENTER_LEFT);

    HBox mHbRealId = new HBox(mLblRealId, mTxfRealId);
    mHbRealId.setSpacing(10);
    mHbRealId.setAlignment(Pos.CENTER_LEFT);

    HBox mHbFirstMeet = new HBox(mLblFirstMeet, mTxfFirstMeet);
    mHbFirstMeet.setSpacing(10);
    mHbFirstMeet.setAlignment(Pos.CENTER_LEFT);

    //以下幾行是對於按鈕的調整 相對於其他部分蠻多行的 約有30行
    //套用星期
    String[] weekChinese = {"一", "二", "三", "四", "五", "六", "日"};
    for(int i = 0; i < weekChinese.length; i++){
      mBtnMtx[0][i] = new Button(weekChinese[i]);
      mBtnMtx[0][i].setBackground(orange);
    }
    //美化按鈕
    for(int i = 1; i < mBtnMtx.length; i++){
      for(int j = 0; j < mBtnMtx[i].length; j++){
        mBtnMtx[i][j] = new Button("");
        mBtnMtx[i][j].setBackground(white);
      }
    }
    //按鈕的最後一排是特別的機關 (上一段的for有調整到最後一排 此段會覆蓋之)
    for(int i = 0; i < mBtnMtx.length; i++){
      mBtnMtx[i][7] = new Button("");
      mBtnMtx[i][7].setBackground(orange);
    }

    //新建等同按鈕橫排數的HBox
    HBox[] mHbAryPlayTime = new HBox[mBtnMtx.length];
    //這個VBox是要裝這些HBox的
    VBox mVbPlayTime = new VBox();
    for(int i = 0; i < mBtnMtx.length; i++){
      mHbAryPlayTime[i] = new HBox();
      for(int j = 0; j < mBtnMtx[i].length; j++){
        // mBtnMtx[i][j] = new Button("");
        mBtnMtx[i][j].setPrefWidth(80);
        mBtnMtx[i][j].setPrefHeight(80);
        // mBtnMtx[i][j].setBackground(white);
        //裝入當列的HBox
        mHbAryPlayTime[i].getChildren().add(mBtnMtx[i][j]);
      }
      //裝入VBox
      mVbPlayTime.getChildren().add(mHbAryPlayTime[i]);
    }

    // HBox mHb5 = new HBox(mLblLabel, mBtnAryLabels);
    // mHb5.setSpacing(10);
    // mHb5.setAlignment(Pos.CENTER_LEFT);

    HBox mHbHistoryLbl = new HBox(mLblHistory);
    mHbHistoryLbl.setSpacing(10);
    mHbHistoryLbl.setAlignment(Pos.CENTER_LEFT);
    HBox mHbHistoryTxa = new HBox(mTxaHistory);
    mHbHistoryTxa.setSpacing(10);
    mHbHistoryTxa.setAlignment(Pos.CENTER_LEFT);

    HBox mHbSaveAs = new HBox(mBtnCreate, mBtnUpdate);
    mHbSaveAs.setSpacing(10);
    mHbSaveAs.setAlignment(Pos.CENTER_LEFT);

    HBox mHbResult = new HBox(mLblResult);
    mHbResult.setSpacing(10);
    mHbResult.setAlignment(Pos.CENTER_LEFT);

    VBox mVbLeftView = new VBox(mHb0LookUp, mHbIndex, mHbRealId, mHbFirstMeet, mVbPlayTime, mHbHistoryLbl, mHbHistoryTxa, mHbSaveAs);
    mVbLeftView.setSpacing(20);
    mVbLeftView.setPadding(new Insets(10, 50, 50, 50));
    VBox mVbRightView = new VBox(mHbResult);

    HBox mVbLeftRightView = new HBox(mVbLeftView, mVbRightView);
    VBox mVbAll = new VBox(mHb0LookUp, mVbLeftRightView);
    mSceneMain = new Scene(mVbAll, 1480, 900);
    mStageMain = new Stage();
    mStageMain.setScene(mSceneMain);
  }

  //設定按鈕組的事件。按鈕設定為預設橘色，按一下變綠色(像是燈的提示那樣)
  //設定按鈕組的事件。按鈕設定為預設白色，按一下變綠色(像是燈的提示那樣)
  private void lightColorSwitchEvent(Button[] mBtnArys, boolean[] lightControler){
    for(int i = 0; i < mBtnArys.length; i++){
      final int j = i;
      mBtnArys[j].setOnAction(e->{
        //如果按下的按鈕為亮
        if(lightControler[j]){
          //關掉
          lightControler[j] = !lightControler[j];
          mBtnArys[j].setBackground(white);
        }
        //如果按下的按鈕為暗
        else {
          //打開指定的燈
          lightControler[j] = !lightControler[j];
          mBtnArys[j].setBackground(green);
        }
      });
    }
  }




  public static void main(String[] args){
   launch(args);
  }
}
