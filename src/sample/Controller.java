package sample;

import applicative.Coordinates;
import applicative.Earth;
import applicative.fileReader;
import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.sun.javafx.geom.Point2D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.awt.*;
import java.net.Inet4Address;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //attributs

    private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;
    Earth Terre = new Earth();
    Group root3D = new Group();
    Group quadri = new Group();
    Group histo = new Group();

    //FXML

    @FXML
    private AnchorPane SplitPane;
    @FXML
    private TextField textYear;
    @FXML
    private Slider slideAnnee;
    @FXML
    private Button Histo;
    @FXML
    private Button Play;
    @FXML
    private Button Pause;
    @FXML
    private Button Stop;
    @FXML
    private RadioButton quart;
    @FXML
    private RadioButton moit;
    @FXML
    private RadioButton fois1;
    @FXML
    private RadioButton fois2;



   @Override
    public void initialize(URL location, ResourceBundle resources) {

       // Load geometry
       ObjModelImporter objImporter = new ObjModelImporter();
       try {
           URL modelURL = this.getClass().getResource("Earth/earth.obj");
           objImporter.read(modelURL);
       } catch (ImportException e) {
           System.out.println(e.getMessage());
       }
       MeshView[] meshViews = objImporter.getImport();
       Group earth = new Group(meshViews);
       root3D.getChildren().add(earth);

       // Add a camera group
       PerspectiveCamera camera = new PerspectiveCamera(true);
       new CameraManager(camera, SplitPane, root3D);

       // Add ambient light
       AmbientLight ambientLight = new AmbientLight(Color.WHITE);
       ambientLight.getScope().addAll(root3D);
       root3D.getChildren().add(ambientLight);

       // Create scene
       SubScene subscene = new SubScene(root3D, 600, 600, true, SceneAntialiasing.BALANCED);
       subscene.setCamera(camera);
       SplitPane.getChildren().addAll(subscene);

       //Affichage de la scène de départ

       creerQuadri();
       dessinQuad();
       creerCylindre();
       //dessinHisto();
       root3D.getChildren().add(quadri);
       //root3D.getChildren().add(histo);


       // sliders
       slideAnnee.setValue(1880);

       //textfields
       textYear.setText("1880");

       //Interaction avec les boutons

           Histo.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent event) {
                   if(Terre.isQuadri()){
                       root3D.getChildren().remove(quadri);
                       root3D.getChildren().add(histo);
                       Terre.setQuadri(false);
                       dessinHisto();
                   }else{
                       root3D.getChildren().remove(histo);
                       root3D.getChildren().add(quadri);
                       Terre.setQuadri(true);
                       dessinQuad();
                   }
               }
           });


           slideAnnee.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent event) {
                   Terre.setAnneechoisie((int) slideAnnee.getValue());
                   if(Terre.isQuadri()) {
                       textYear.setText((int) Math.round(slideAnnee.getValue()) + "");
                       //creerQuadri();
                       dessinQuad();
                   }else {
                       textYear.setText((int) Math.round(slideAnnee.getValue()) + "");
                       //creerCylindre();
                       dessinHisto();
                   }
               }
           });

            textYear.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent e) {
                    try {
                        if(Terre.isQuadri()) {
                            Terre.setAnneechoisie(Integer.parseInt(textYear.getText()));
                            slideAnnee.setValue((Double.parseDouble(textYear.getText())));
                            //creerQuadri();
                            dessinQuad1();
                        }else {
                            textYear.setText((int) Math.round(slideAnnee.getValue()) + "");
                            Terre.setAnneechoisie((int) slideAnnee.getValue());
                            //creerCylindre();
                            dessinHisto();
                        }
                    } catch (Exception ex) {
                    }
                }
            });

            fois2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event){
                    Terre.setValVitesse(2);
                }

           });
           fois1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
               @Override
               public void handle(MouseEvent event){
                   Terre.setValVitesse(1);
               }

           });

           moit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
               @Override
               public void handle(MouseEvent event){
                   Terre.setValVitesse(0.5);
               }

           });

           quart.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
               @Override
               public void handle(MouseEvent event){
                   Terre.setValVitesse(0.25);
               }

           });

         //PickResult(Node node, Point3D point, double distance, int face, Point3D normal, Point2D texCoord) pick = new PickResult();


       //Animation
           AnimationTimer animation = new AnimationTimer() {
               @Override
               public void handle(long currentNanoTime) {
                   int year=Terre.getAnneechoisie();
                   slideAnnee.setValue(year);
                   //Tentative de réglage de vitesse
                   /*
                   int val = 100000;
                   val=((int)Math.round(val/Terre.getValVitesse()));
                    for(int i =0; i<val;  i++ ){

                    }
                   */
                   if(Terre.isQuadri() && year<2021 ){
                       System.out.println(year);
                       slideAnnee.setValue(year);
                       textYear.setText((int) Math.round(slideAnnee.getValue()) + "");
                       root3D.getChildren().remove(quadri);
                       //creerQuadri();
                       dessinQuad1();
                       root3D.getChildren().add(quadri);
                   } else if(!Terre.isQuadri() && year<2021 ){
                       System.out.println(year);
                       slideAnnee.setValue(year);
                       textYear.setText((int) Math.round(slideAnnee.getValue()) + "");
                       root3D.getChildren().remove(histo);
                       //creerCylindre();
                       dessinHisto();
                       root3D.getChildren().add(histo);
                   } else if(year>=2021){
                       this.stop();
                       year = 1880;
                       Terre.setAnneechoisie(year);
                   }
                   year += (int)Math.round(Terre.getValVitesse())*2;
                   Terre.setAnneechoisie(year);
               }
           };

           //Bouton associé à l'animation
           Play.setOnAction(event -> { animation.start(); });
           Pause.setOnAction(event -> { animation.stop(); });
           Stop.setOnAction(event -> {
               animation.stop();
               Terre.setAnneechoisie(1880);
               textYear.setText("1880");
               slideAnnee.setValue((Double.parseDouble(textYear.getText())));

       });
     }

        public void dessinQuad(){
            Group quadri = new Group();
            int year = Terre.getAnneechoisie();
            Color mat = Color.BROWN;
            for (int i=0; i<Terre.getPYear(year).size(); i++) {
                PhongMaterial currentMaterial = new PhongMaterial();
                Coordinates cods = new Coordinates(Terre.getLon().get(i),Terre.getLat().get(i));
                Double valeur = Terre.getPZoneYear(cods,year);
                MeshView mesh = Terre.getMeshList().get(cods);
                if(mesh != null) {
                    if (valeur  > 8.0 && valeur  < 9.0) {
                        mat = Color.rgb(232, 10, 6, 0.001);
                    } else if (valeur  > 6.0 && valeur  < 8.0) {
                        mat = Color.rgb(240, 58, 31, 0.001);
                    } else if (valeur  > 4.0 && valeur  < 6.0) {
                        mat = Color.rgb(255, 108, 31, 0.001);
                    } else if (valeur  > 2.0 && valeur  < 4.0) {
                        mat = Color.rgb(240, 167, 31, 0.001);
                    } else if (valeur  > 0.0 && valeur  < 2.0) {
                        mat = Color.rgb(232, 255, 31, 0.001);
                    } else if (valeur  > -2.0 && valeur  < 0.0) {
                        mat = Color.rgb(66, 255, 3, 0.001);
                    } else if (valeur  > -4.0 && valeur  < -2.0) {
                        mat = Color.rgb(91, 244, 255, 0.001);
                    } else if (valeur  > -6.5 && valeur  < -4.0) {
                        mat = Color.rgb(33, 161, 255, 0.001);
                    }
                    currentMaterial.setDiffuseColor(mat);
                    currentMaterial.setSpecularColor(mat);
                    mesh.setMaterial(currentMaterial);
                }
            }
        }

        public void dessinQuad1(){
            Group quadri = new Group();
            int year = Terre.getAnneechoisie();
            Color mat = Color.BROWN;
            for (int lat=-88;lat<=88;lat+=4) {
                for (int lon=-178;lon<=178;lon+=4) {
                    PhongMaterial currentMaterial = new PhongMaterial();
                    Coordinates cods = new Coordinates(lon,lat);
                    Double valeur = Terre.getPZoneYear(cods,year);
                    MeshView mesh = Terre.getMeshList().get(cods);
                    if (mesh != null) {
                        if (valeur  > 8.0 && valeur  < 9.0) {
                            mat = Color.rgb(232, 10, 6, 0.001);
                        } else if (valeur  > 6.0 && valeur  < 8.0) {
                            mat = Color.rgb(240, 58, 31, 0.001);
                        } else if (valeur  > 4.0 && valeur  < 6.0) {
                            mat = Color.rgb(255, 108, 31, 0.001);
                        } else if (valeur  > 2.0 && valeur  < 4.0) {
                            mat = Color.rgb(240, 167, 31, 0.001);
                        } else if (valeur  > 0.0 && valeur  < 2.0) {
                            mat = Color.rgb(232, 255, 31, 0.001);
                        } else if (valeur  > -2.0 && valeur  < 0.0) {
                            mat = Color.rgb(66, 255, 3, 0.001);
                        } else if (valeur  > -4.0 && valeur  < -2.0) {
                            mat = Color.rgb(91, 244, 255, 0.001);
                        } else if (valeur  > -6.5 && valeur  < -4.0) {
                            mat = Color.rgb(33, 161, 255, 0.001);
                        }
                        currentMaterial.setDiffuseColor(mat);
                        currentMaterial.setSpecularColor(mat);
                        mesh.setMaterial(currentMaterial);
                    }
                }
            }
        }

        public void dessinHisto(){
            Group histo = new Group();
            int year = Terre.getAnneechoisie();
            Color mat = Color.BROWN;
            for (int lat=-88;lat<=88;lat+=4) {
                for (int lon=-178;lon<=178;lon+=4) {
                    PhongMaterial currentMaterial = new PhongMaterial();
                    Coordinates cods = new Coordinates(lon,lat);
                    Double valeur = Terre.getPZoneYear(cods,year);
                    Cylinder cylindre = Terre.getCylinderList().get(cods);
                    if (cylindre != null) {
                        if (valeur > 8.0 && valeur  < 9.0) {
                            mat = Color.rgb(232, 10, 6, 1);
                            cylindre.setHeight(valeur * 0.5);
                            //cylindre.setHeight(1.50);
                        } else if (valeur > 6.0 && valeur < 8.0) {
                            mat = Color.rgb(240, 58, 31, 1);
                            cylindre.setHeight(valeur * 0.5);
                            //cylindre.setHeight(1.40);
                        } else if (valeur > 4.0 && valeur < 6.0) {
                            mat = Color.rgb(255, 108, 31, 1);
                            cylindre.setHeight((double)valeur * 0.5);
                            //cylindre.setHeight(1.30);
                        } else if (valeur > 2.0 && valeur < 4.0) {
                            mat = Color.rgb(240, 167, 31, 1);
                            cylindre.setHeight((double)valeur * 0.5);
                            //cylindre.setHeight(1.20);
                        } else if (valeur > 0.0 && valeur < 2.0) {
                            mat = Color.rgb(232, 255, 31, 1);
                            cylindre.setHeight(valeur * 0.5);
                            //cylindre.setHeight(1.10);
                        } else if (valeur  > -2.0 && valeur  < 0.0) {
                            mat = Color.rgb(66, 255, 3, 1);
                            cylindre.setHeight(valeur * -0.5);
                        } else if (valeur  > -4.0 && valeur  < -2.0) {
                            mat = Color.rgb(91, 244, 255, 1);
                            cylindre.setHeight(valeur * -0.5);
                        } else if (valeur  > -6.5 && valeur  < -4.0) {
                            mat = Color.rgb(33, 161, 255, 1);
                            cylindre.setHeight(valeur * -0.5);
                        }
                        currentMaterial.setDiffuseColor(mat);
                        currentMaterial.setSpecularColor(mat);
                        cylindre.setRadius(0.02);
                        cylindre.setMaterial(currentMaterial);
                    }
                }
            }
        }

        public void creerQuadri(){
        int year =Terre.getAnneechoisie();
            for (int i=0; i<Terre.getPYear(year).size(); i++) {
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(Color.GREY);
                material.setSpecularColor(Color.GREY);
                Point3D topRight = geoCoordTo3dCoord(Terre.getLat().get(i) + 2, Terre.getLon().get(i) + 2, 1.01f);
                Point3D topLeft = geoCoordTo3dCoord(Terre.getLat().get(i) + 2, Terre.getLon().get(i) - 2, 1.01f);
                Point3D bottomRight = geoCoordTo3dCoord(Terre.getLat().get(i) - 2, Terre.getLon().get(i) + 2, 1.01f);
                Point3D bottomLeft = geoCoordTo3dCoord(Terre.getLat().get(i) - 2, Terre.getLon().get(i) - 2, 1.01f);
                addQuadrilateral(new Coordinates(Terre.getLon().get(i),Terre.getLat().get(i)),quadri, topRight, bottomRight, topLeft, bottomLeft, material);
            }
        }

        public void creerCylindre(){
            int year =Terre.getAnneechoisie();
            for (int i=0; i<Terre.getPYear(year).size(); i++) {
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(Color.GREY);
                material.setSpecularColor(Color.GREY);
                Point3D origine = geoCoordTo3dCoord(Terre.getLat().get(i), Terre.getLon().get(i),1f);
                Point3D target = geoCoordTo3dCoord(Terre.getLat().get(i),Terre.getLon().get(i),1.1f);
                createLine(new Coordinates(Terre.getLon().get(i),Terre.getLat().get(i)),histo,origine,target, material);
            }
        }


        public static Point3D geoCoordTo3dCoord(float lat, float lon, float radius){
            float lat_coord= lat+TEXTURE_LAT_OFFSET;
            float lon_coord= lon+TEXTURE_LON_OFFSET;
            return new Point3D(
                    -java.lang.Math.sin(java.lang.Math.toRadians(lon_coord))
                            *java.lang.Math.cos(java.lang.Math.toRadians(lat_coord))*radius,
                    -java.lang.Math.sin(java.lang.Math.toRadians(lat_coord))*radius,
                    java.lang.Math.cos(java.lang.Math.toRadians(lon_coord))
                            *java.lang.Math.cos(java.lang.Math.toRadians(lat_coord))*radius);
        }

        // From Rahel LÃ¼thy : https://netzwerg.ch/blog/2015/03/22/javafx-3d-line/
        public Cylinder createLine(Coordinates co, Group parent, Point3D origin, Point3D target, PhongMaterial material) {
            Point3D yAxis = new Point3D(0, 1, 0);
            Point3D diff = target.subtract(origin);
            double height = diff.magnitude();

            Point3D mid = target.midpoint(origin);
            Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

            Point3D axisOfRotation = diff.crossProduct(yAxis);
            double angle = Math.acos(diff.normalize().dotProduct(yAxis));
            Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

            Cylinder line = new Cylinder(0.01f, height);

            line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

            line.setMaterial(material);
            Terre.getCylinderList().put(co,line);
            parent.getChildren().add(line);
            return line;
        }

        private void addQuadrilateral (Coordinates co, Group parent, Point3D topRight, Point3D bottomRight, Point3D topLeft, Point3D bottomLeft, PhongMaterial material){
            final TriangleMesh triangleMesh = new TriangleMesh();

            final float[] points = {
                    (float)topRight.getX(), (float)topRight.getY(),(float)topRight.getZ(),
                    (float)topLeft.getX(),(float)topLeft.getY(),(float)topLeft.getZ(),
                    (float)bottomLeft.getX(),(float)bottomLeft.getY(),(float)bottomLeft.getZ(),
                    (float)bottomRight.getX(),(float)bottomRight.getY(),(float)bottomRight.getZ()
            };

            final float[] texCoord = {
                    1,1,
                    1,0,
                    0,1,
                    0,0
            };

            final int[] faces = {
                    0,1,1,0,2,2,
                    0,1,2,2,3,3
            };

            triangleMesh.getPoints().setAll(points);
            triangleMesh.getTexCoords().setAll(texCoord);
            triangleMesh.getFaces().setAll(faces);

            final MeshView meshView = new MeshView(triangleMesh);
            meshView.setMaterial(material);
            parent.getChildren().addAll(meshView);
            Terre.getMeshList().put(co,meshView);
        }
    }




