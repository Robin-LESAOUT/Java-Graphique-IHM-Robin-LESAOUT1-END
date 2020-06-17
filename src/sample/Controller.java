package sample;

import applicative.Earth;
import applicative.fileReader;
import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;

    @FXML
    private AnchorPane SplitPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {



                //Create a Pane et graph scene root for the 3D content
                Group root3D = new Group();
                Earth Terre = new Earth();

                // Load geometry
                ObjModelImporter objImporter = new ObjModelImporter();
                try{
                    URL modelURL= this.getClass().getResource("Earth/earth.obj");
                    objImporter.read(modelURL);
                }catch(ImportException e){
                    System.out.println(e.getMessage());
                }
                MeshView[] meshViews=objImporter.getImport();
                Group earth = new Group(meshViews);
                root3D.getChildren().add(earth);


                //Creation des Materials

                PhongMaterial material10 = new PhongMaterial();
                Color mat10 = Color.rgb(232,10,6,0.001);
                material10.setDiffuseColor(mat10);
                material10.setSpecularColor(mat10);

                PhongMaterial material8 = new PhongMaterial();
                Color mat8 = Color.rgb(240,58,31,0.001);
                material8.setDiffuseColor(mat8);
                material8.setSpecularColor(mat8);

                PhongMaterial material6 = new PhongMaterial();
                Color mat6 = Color.rgb(255,108,31,0.001);
                material6.setDiffuseColor(mat6);
                material6.setSpecularColor(mat6);

                PhongMaterial material4 = new PhongMaterial();
                Color mat4 = Color.rgb(240,167,31,0.001);
                material4.setDiffuseColor(mat4);
                material4.setSpecularColor(mat4);

                PhongMaterial material2 = new PhongMaterial();
                Color mat2 = Color.rgb(232,255,31,0.001);
                material2.setDiffuseColor(mat2);
                material2.setSpecularColor(mat2);

                PhongMaterial material22 = new PhongMaterial();
                Color mat22 = Color.rgb(255,255,255,0.001);
                material22.setDiffuseColor(mat22);
                material22.setSpecularColor(mat22);

                PhongMaterial material44 = new PhongMaterial();
                Color mat44 = Color.rgb(91,244,255,0.001);
                material44.setDiffuseColor(mat44);
                material44.setSpecularColor(mat44);

                PhongMaterial material66 = new PhongMaterial();
                Color mat66 = Color.rgb(33,161,255,0.001);
                material66.setDiffuseColor(mat66);
                material66.setSpecularColor(mat66);

                PhongMaterial material88 = new PhongMaterial();
                Color mat88 = Color.rgb(61,108,248,0.001);
                material88.setDiffuseColor(mat88);
                material88.setSpecularColor(mat88);

                PhongMaterial material100 = new PhongMaterial();
                Color mat100 = Color.rgb(0,55,255,0.001);
                material100.setDiffuseColor(mat100);
                material100.setSpecularColor(mat100);

                //dessinQuad(root3D,material1,material2);

                // Add a camera group
                PerspectiveCamera camera = new PerspectiveCamera(true);
                new CameraManager(camera, SplitPane, root3D);

                // Add point light
                PointLight light = new PointLight(Color.WHITE);
                light.setTranslateX(-180);
                light.setTranslateY(-90);
                light.setTranslateZ(-120);
                light.getScope().addAll(root3D);
                root3D.getChildren().add(light);

                // Add ambient light
                AmbientLight ambientLight = new AmbientLight(Color.WHITE);
                ambientLight.getScope().addAll(root3D);
                root3D.getChildren().add(ambientLight);

                // Create scene
                SubScene subscene = new SubScene(root3D, 600, 600,true, SceneAntialiasing.BALANCED);
                subscene.setCamera(camera);
                SplitPane.getChildren().addAll(subscene);
            }


            // From Rahel LÃ¼thy : https://netzwerg.ch/blog/2015/03/22/javafx-3d-line/
            public Cylinder createLine(Point3D origin, Point3D target) {
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

                return line;
            }

            public void dessinQuad(Group parent,PhongMaterial mat1, PhongMaterial mat2){
                int compt=1;
                for (int lat = -90; lat < 90; lat += 2) {
                    PhongMaterial currentMaterial;
                    for (int lon = -180; lon < 180; lon += 2) {
                        if (lat % 4 == 0) {
                            if (compt%2==0){
                                currentMaterial = mat1;
                            }
                            else{
                                currentMaterial = mat2;
                            }
                        } else {
                            if (compt%2!=0){
                                currentMaterial = mat1;
                            }
                            else {
                                currentMaterial = mat2;
                            }
                        }

                        Point3D topRight = geoCoordTo3dCoord(lat + 2, lon, 1.01f);
                        Point3D topLeft = geoCoordTo3dCoord(lat, lon, 1.01f);
                        Point3D bottomRight = geoCoordTo3dCoord(lat + 2, lon + 2, 1.01f);
                        Point3D bottomLeft = geoCoordTo3dCoord(lat, lon + 2, 1.01f);
                        addQuadrilateral(parent, topRight, bottomRight, topLeft, bottomLeft, currentMaterial);
                        compt++;
                    }
                }
            }
/*
            public void displayTown(Group parent, String name, float latitude, float longitude){
                Point3D lieu = geoCoordTo3dCoord(latitude,longitude,1);
                Sphere sphere = new Sphere(0.01);
                //sphere.setStyle("-fx-background-color:red");
                sphere.setTranslateX(lieu.getX());
                sphere.setTranslateY(lieu.getY());
                sphere.setTranslateZ(lieu.getZ());
                parent.getChildren().add(sphere);
            }*/

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
            private void addQuadrilateral (Group parent, Point3D topRight, Point3D bottomRight, Point3D topLeft, Point3D bottomLeft, PhongMaterial material){
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
            }
    }




