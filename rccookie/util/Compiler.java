package rccookie.util;

import java.util.ArrayList;

import greenfoot.*;
import rccookie.app.*;
import rccookie.data.*;
import rccookie.data.json.*;
import rccookie.event.*;
import rccookie.event.greenfoot.*;
import rccookie.game.*;
import rccookie.game.physics.*;
import rccookie.game.util.*;
import rccookie.geometry.*;
import rccookie.learning.*;
//import rccookie.rendering.*;
import rccookie.ui.advanced.*;
import rccookie.ui.basic.*;

public class Compiler {
    public static void testStuff(){
        Console.log("Starting test...\n");

        Console.log("rccookie.util.Console");
        Console.log();

        /*Console.log("Initializing Greenfoot");
        WorldHandler.initialise();
        Simulation.initialize();
        GreenfootUtil.initialise(GreenfootUtilDelegateIDE.getInstance());
        Console.log("Successfully initialized Greenfoot");*/

        Console.log("app"); {
            Console.log("Item");
            new Item() { };
            Console.log("World");
            new rccookie.app.World(1, 1);
            Console.log("Application");
            new Application("Hello").dispose();
        }

        Console.log("data"); {
            Console.log("Saveable");
            new Saveable(){
				private static final long serialVersionUID = 1L;
                public String getSaveName() { return ""; }
                public void setSaveName(String n) { }
            };
            Console.log("SerialTools");
            @SuppressWarnings("unused")
            Object o = SerialTools.class;
            Console.log("json"); {
                Console.log("Json");
                Json.validateName("name");
            }
        }

        Console.log("event"); {
            Console.log("Threads");
            Threads.runParralel(info -> { });
            Console.log("greenfoot"); {
                Console.log("KeyListener");
                new KeyListener("a");
            }
        }

        Console.log("game"); {
            Console.log("Advanced Actor");
            new AdvancedActor() { private static final long serialVersionUID = 1L; };

            Console.log("physics"); {
                Console.log("CircleCollider");
                new CircleCollider(1, new Vector2D());
                Console.log("Collider");
                new BoxCollider(new Actor() { });
                Console.log("Cylinder");
                new Cylinder(1, 1);
                Console.log("PhysicalActor");
                new PhysicalActor() { private static final long serialVersionUID = 1L; };
            }

            Console.log("util"); {
                Console.log("Time");
                new Time();
                Console.log("ActorTable");
                new ActorTable<Actor>(1, 1);
            }
        }

        Console.log("learning"); {
            Console.log("OnOffFunction");
            new OnOffFunction();
            Console.log("SigmoidFunction");
            new SigmoidFunction();
            Console.log("Neuron");
            new Neuron();
            Console.log("Edge");
            new rccookie.learning.Edge(null);
            Console.log("KeyDownNeuron");
            new KeyDownNeuron("space");
            Console.log("StaticNeuron");
            new StaticNeuron();
            Console.log("StaticEdge");
            new StaticEdge(null);
            Console.log("Network");
            new Network(new ArrayList<>(), 1);
            Console.log("Generation");
            //new Generation(new ArrayList<>()){};
        }

        Console.log("geometry"); {
            Console.log("Vector3D");
            new Vector3D();
            Console.log("Vector2D");
            new Vector2D();
            Console.log("Ray2D");
            new Ray2D(new Vector2D(1));
            Console.log("Ray3D");
            new Ray3D(new Vector3D(1));
            Console.log("Edge2D");
            new Edge2D(new Vector2D(), new Vector2D(1));
            Console.log("Edge3D");
            new Edge3D(new Vector3D(), new Vector3D(1));
            Console.log("Transform");
            new Transform3D();
            Console.log("Transform2D");
            new Transform2D();
            Console.log("Rotation");
            new Rotation();
            Console.log("Geomentry");
            Geometry.floor(1, 1);
            Console.log("Line");
            new rccookie.geometry.Ray3D(new Vector3D(1));
            Console.log("Physics");
            Physics.Mechanics.distTraveled(1, 1);
            Physics.Relativity.kineticEnergy(1, 1);
            Physics.Gravitation.temperatur(1);
        }

        // Rendering

        Console.log("ui"); {
            Console.log("basic"); {
                Console.log("Text");
                new Text();
                Console.log("Button");
                new Button("Hello World!");
                Console.log("Fade");
                Fade.fadeIn(Color.BLACK, 1);
                Console.log("ObjectAdder");
                new ObjectAdder(new Actor() { });
                Console.log("Slider");
                new Slider();
                Console.log("UIPanel");
                new UIPanel(new greenfoot.World(1, 1, 1) { });
                Console.log("UIWorld");
                new UIWorld(100, 100, 1);
            }

            Console.log("advanced"); {
                Console.log("Container");
                new Container(10, 10, 1);
                Console.log("Camera");
                Console.log(">>" + Camera.BACKGROUND);
                Console.log("DropDownMenu");
                String[] temp = {"World", "!"};
                new DropDownMenu("Hello", temp);
                Console.log("FpsDisplay");
                new FpsDisplay();
                Console.log("ExponentialSlider");
                new ExponentialSlider(0, 1, 100, Color.BLACK, Color.BLACK);
                Console.log("TextField");
                Console.log(">>" + TextField.BORDER);
                //new TextField(new Text("Hello"), 100, 50);
                Console.log("NumberField");
                Console.log(">>" + NumberField.DEFAULT_RETURN);
                //new NumberField();
                Console.log("Scoreboard");
                new Scoreboard(100, 100);
                Console.log("Switch");
                new Switch();
            }
        }

        Console.log("util"); {
            Console.log("TagTable/ ClassTag");
            ClassTag.tag(Compiler.class, "util");
            Console.log("Lists");
            Lists.any(new ArrayList<>(), info -> true);
            Console.log("Grid/ AbstractTable/ Table/ ActorTable");
            new ActorTable<>(1, 1);
            Console.log("InfinitiveGrid/ Plane/ Map");
            new Map<>();
            Console.log("Line");
            Console.log(">>", Line.COMPARATOR);
            /*Console.log("TreeLine");
            new rccookie.util.TreeLine<Integer, Object>() {
                static final long serialVersionUID = 1L;
                public Integer add(Object value) { return null; }
                public Integer addDown(Object value) { return null; }
                public Integer next(Integer index) { return null; }
                public Integer nextDown(Integer index) { return null; }
                protected IntLine<Object> clone() { return null; }
            };
            /*Console.log("Line/ TreeLine/ IntLine");
            new IntLine<>();
            Console.log("DoubleLine");
            new DoubleLine<>(1);*/
        }

        Console.newLine();
        Console.log("Successfully testet.");
    }
}
