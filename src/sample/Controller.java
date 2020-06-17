package sample;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.*;
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

    @FXML
    private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



                //Create a Pane et graph scene root for the 3D content
                Group root3D = new Group();
                Pane pane3D = new Pane(root3D);

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


                // Draw city on the earth
                displayTown(root3D,"Brest", 48.447911f,-4.418539f);
                displayTown(root3D,"Marseille", 43.435555f,5.213611f);
                displayTown(root3D,"New York", 40.639751f,-73.778925f);
                displayTown(root3D,"Cape Town",  -33.964806f,18.601667f);
                displayTown(root3D,"Istanbul", 48.447911f,28.814606f);
                displayTown(root3D,"Reykjavik", 64.13f,-21.940556f);
                displayTown(root3D,"Singapore", 1.350189f,103.994433f);
                displayTown(root3D,"Seoul", 37.469075f,126.450517f);

                PhongMaterial material1 = new PhongMaterial();
                Color green = Color.rgb(0,255,0,0.001);
                material1.setDiffuseColor(green);
                material1.setSpecularColor(green);

                PhongMaterial material2 = new PhongMaterial();
                Color red = Color.rgb(255,0,0,0.001);
                material2.setDiffuseColor(red);
                material2.setSpecularColor(red);

                dessinQuad(root3D,material1,material2);

                // Add a camera group
                PerspectiveCamera camera = new PerspectiveCamera(true);
                new CameraManager(camera, pane3D, root3D);

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
                Scene scene = new Scene(pane3D, 600, 600, true);
                scene.setCamera(camera);
                scene.setFill(Color.GREY);
                scene.setFill(Color.gray(0.2));
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

            public void displayTown(Group parent, String name, float latitude, float longitude){
                Point3D lieu = geoCoordTo3dCoord(latitude,longitude,1);
                Sphere sphere = new Sphere(0.01);
                //sphere.setStyle("-fx-background-color:red");
                sphere.setTranslateX(lieu.getX());
                sphere.setTranslateY(lieu.getY());
                sphere.setTranslateZ(lieu.getZ());
                parent.getChildren().add(sphere);
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




