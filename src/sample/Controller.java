package sample;

        import java.net.URL;
        import java.util.ResourceBundle;

        import javafx.animation.AnimationTimer;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.geometry.Point3D;
        import javafx.scene.Group;
        import javafx.scene.PerspectiveCamera;
        import javafx.scene.PointLight;
        import javafx.scene.Scene;
        import javafx.scene.SceneAntialiasing;
        import javafx.scene.SubScene;
        import javafx.scene.layout.Pane;
        import javafx.scene.paint.Color;
        import javafx.scene.paint.PhongMaterial;
        import javafx.scene.shape.Box;

public class Controller implements Initializable {

    @FXML
    private Pane pane3D;

    @Override
    public void initialize(URL location, ResourceBundle ressources) {

        //Create a Pane et graph scene root for the 3D content
        Group root3D = new Group();
        //Pane pane3D = new Pane(root3D);

        //Create cube shape
        Box cube = new Box(1, 1, 1);
        Box cuber = new Box(1, 1, 1);
        Box cubeV = new Box(1, 1, 1);

        //Create Material
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.BLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.RED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial vertMaterial = new PhongMaterial();
        vertMaterial.setDiffuseColor(Color.GREEN);
        vertMaterial.setSpecularColor(Color.GREEN);

        //Set it to the cube
        cube.setMaterial(blueMaterial);
        cuber.setMaterial(redMaterial);
        cubeV.setMaterial(vertMaterial);

        //Add the cube to this node
        root3D.getChildren().add(cube);
        root3D.getChildren().add(cuber);
        root3D.getChildren().add(cubeV);

        //Add a camera group
        PerspectiveCamera camera = new PerspectiveCamera(true);
        // Group cameraGroup = new Group(camera);
        // root3D.getChildren().add(cameraGroup);

        //Rotate then move the camera
        // Rotate ry = new Rotate();
        // ry.setAxis(Rotate.Y_AXIS);
        // ry.setAngle(-15);

        //Translate tz = new Translate();
        //tz.setZ(-10);
        //tz.setY(-1);

        cuber.setTranslateX(1.5);
        cubeV.setTranslateY(-1.5);

        //cameraGroup.getTransforms().addAll(ry,tz);

        // Add point light
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(-180);
        light.setTranslateY(-90);
        light.setTranslateZ(-120);
        light.getScope().addAll(root3D);
        root3D.getChildren().add(light);


        // Create scene
        SubScene subscene = new SubScene(pane3D,600,600,true,SceneAntialiasing.BALANCED);
        subscene.setCamera(camera);
        subscene.setFill(Color.GREY);
        pane3D.getChildren().addAll(subscene);


        //add animation timer
        double rotationSpeed=15;
        final long startNanoTime= System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime)/1000000000.0;
                cubeV.setRotationAxis(new Point3D(0,1,0));
                cubeV.setRotate(rotationSpeed*t);
            }
        }.start();

        new CameraManager(camera, pane3D, root3D);
    }
}


