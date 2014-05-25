package DiezeFond;

import DiezeFond.AppGUI;
import DiezeFond.EnvironmentClock;
import DiezeFond.EnvironmentGUI;
import DiezeFond.EnvironmentMove;
import DiezeFond.GridManager;
import DiezeFond.RobotActionManager;
import DiezeFond.RobotMemory;
import fr.sma.speadl.ActionHandler;
import fr.sma.speadl.EnvironmentRenderer;
import fr.sma.speadl.EnvironmentUpdater;
import fr.sma.speadl.GridDataProvider;
import fr.sma.speadl.GridUpdater;
import fr.sma.speadl.MemoryHandler;
import fr.sma.speadl.MoveHandler;

@SuppressWarnings("all")
public abstract class Ecosystem {
  @SuppressWarnings("all")
  public interface Requires {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends Ecosystem.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements Ecosystem.Component, Ecosystem.Parts {
    private final Ecosystem.Requires bridge;
    
    private final Ecosystem implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final Ecosystem implem, final Ecosystem.Requires b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Robot {
    @SuppressWarnings("all")
    public interface Requires {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public MoveHandler environmentMoveHandler();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public ActionHandler robotActionHandler();
    }
    
    
    @SuppressWarnings("all")
    public interface Component extends Ecosystem.Robot.Provides {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public RobotMemory.Component memory();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public RobotActionManager.Component actionManager();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements Ecosystem.Robot.Component, Ecosystem.Robot.Parts {
      private final Ecosystem.Robot.Requires bridge;
      
      private final Ecosystem.Robot implementation;
      
      public void start() {
        assert this.memory != null: "This is a bug.";
        ((RobotMemory.ComponentImpl) this.memory).start();
        assert this.actionManager != null: "This is a bug.";
        ((RobotActionManager.ComponentImpl) this.actionManager).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.memory == null: "This is a bug.";
        assert this.implem_memory == null: "This is a bug.";
        this.implem_memory = this.implementation.make_memory();
        if (this.implem_memory == null) {
        	throw new RuntimeException("make_memory() in DiezeFond.Ecosystem$Robot should not return null.");
        }
        this.memory = this.implem_memory._newComponent(new BridgeImpl_memory(), false);
        assert this.actionManager == null: "This is a bug.";
        assert this.implem_actionManager == null: "This is a bug.";
        this.implem_actionManager = this.implementation.make_actionManager();
        if (this.implem_actionManager == null) {
        	throw new RuntimeException("make_actionManager() in DiezeFond.Ecosystem$Robot should not return null.");
        }
        this.actionManager = this.implem_actionManager._newComponent(new BridgeImpl_actionManager(), false);
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final Ecosystem.Robot implem, final Ecosystem.Robot.Requires b, final boolean doInits) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null: "This is a bug.";
        implem.selfComponent = this;
        
        // prevent them to be called twice if we are in
        // a specialized component: only the last of the
        // hierarchy will call them after everything is initialised
        if (doInits) {
        	initParts();
        	initProvidedPorts();
        }
        
      }
      
      public final ActionHandler robotActionHandler() {
        return this.actionManager.actionHandler();
      }
      
      private RobotMemory.Component memory;
      
      private RobotMemory implem_memory;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_memory implements RobotMemory.Requires {
      }
      
      
      public final RobotMemory.Component memory() {
        return this.memory;
      }
      
      private RobotActionManager.Component actionManager;
      
      private RobotActionManager implem_actionManager;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_actionManager implements RobotActionManager.Requires {
        public final MemoryHandler memoryHandler() {
          return Ecosystem.Robot.ComponentImpl.this.memory.memoryHandler();
        }
        
        public final MoveHandler moveHandler() {
          return Ecosystem.Robot.ComponentImpl.this.bridge.environmentMoveHandler();
        }
      }
      
      
      public final RobotActionManager.Component actionManager() {
        return this.actionManager;
      }
    }
    
    
    /**
     * Used to check that two components are not created from the same implementation,
     * that the component has been started to call requires(), provides() and parts()
     * and that the component is not started by hand.
     * 
     */
    private boolean init = false;;
    
    /**
     * Used to check that the component is not started by hand.
     */
    private boolean started = false;;
    
    private Ecosystem.Robot.ComponentImpl selfComponent;
    
    /**
     * Can be overridden by the implementation.
     * It will be called automatically after the component has been instantiated.
     * 
     */
    protected void start() {
      if (!this.init || this.started) {
      	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
      }
      
    }
    
    /**
     * This can be called by the implementation to access the provided ports.
     * 
     */
    protected Ecosystem.Robot.Provides provides() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected Ecosystem.Robot.Requires requires() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
      }
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected Ecosystem.Robot.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract RobotMemory make_memory();
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract RobotActionManager make_actionManager();
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized Ecosystem.Robot.Component _newComponent(final Ecosystem.Robot.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Robot has already been used to create a component, use another one.");
      }
      this.init = true;
      Ecosystem.Robot.ComponentImpl comp = new Ecosystem.Robot.ComponentImpl(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private Ecosystem.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Ecosystem.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Ecosystem.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Ecosystem.Parts eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Environment {
    @SuppressWarnings("all")
    public interface Requires {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public ActionHandler robotActionHandler();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public EnvironmentRenderer guiRenderEnvironment();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public MoveHandler environmentMovedHandler();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public EnvironmentUpdater updateEnvironment();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public GridDataProvider dataProvider();
    }
    
    
    @SuppressWarnings("all")
    public interface Component extends Ecosystem.Environment.Provides {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public EnvironmentClock.Component clock();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public EnvironmentMove.Component move();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public GridManager.Component gridManager();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements Ecosystem.Environment.Component, Ecosystem.Environment.Parts {
      private final Ecosystem.Environment.Requires bridge;
      
      private final Ecosystem.Environment implementation;
      
      public void start() {
        assert this.clock != null: "This is a bug.";
        ((EnvironmentClock.ComponentImpl) this.clock).start();
        assert this.move != null: "This is a bug.";
        ((EnvironmentMove.ComponentImpl) this.move).start();
        assert this.gridManager != null: "This is a bug.";
        ((GridManager.ComponentImpl) this.gridManager).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.clock == null: "This is a bug.";
        assert this.implem_clock == null: "This is a bug.";
        this.implem_clock = this.implementation.make_clock();
        if (this.implem_clock == null) {
        	throw new RuntimeException("make_clock() in DiezeFond.Ecosystem$Environment should not return null.");
        }
        this.clock = this.implem_clock._newComponent(new BridgeImpl_clock(), false);
        assert this.move == null: "This is a bug.";
        assert this.implem_move == null: "This is a bug.";
        this.implem_move = this.implementation.make_move();
        if (this.implem_move == null) {
        	throw new RuntimeException("make_move() in DiezeFond.Ecosystem$Environment should not return null.");
        }
        this.move = this.implem_move._newComponent(new BridgeImpl_move(), false);
        assert this.gridManager == null: "This is a bug.";
        assert this.implem_gridManager == null: "This is a bug.";
        this.implem_gridManager = this.implementation.make_gridManager();
        if (this.implem_gridManager == null) {
        	throw new RuntimeException("make_gridManager() in DiezeFond.Ecosystem$Environment should not return null.");
        }
        this.gridManager = this.implem_gridManager._newComponent(new BridgeImpl_gridManager(), false);
        
      }
      
      protected void initProvidedPorts() {
        assert this.dataProvider == null: "This is a bug.";
        this.dataProvider = this.implementation.make_dataProvider();
        if (this.dataProvider == null) {
        	throw new RuntimeException("make_dataProvider() in DiezeFond.Ecosystem$Environment should not return null.");
        }
        
      }
      
      public ComponentImpl(final Ecosystem.Environment implem, final Ecosystem.Environment.Requires b, final boolean doInits) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null: "This is a bug.";
        implem.selfComponent = this;
        
        // prevent them to be called twice if we are in
        // a specialized component: only the last of the
        // hierarchy will call them after everything is initialised
        if (doInits) {
        	initParts();
        	initProvidedPorts();
        }
        
      }
      
      public final MoveHandler environmentMovedHandler() {
        return this.move.moveHandler();
      }
      
      public final EnvironmentUpdater updateEnvironment() {
        return this.gridManager.updateEnvironment();
      }
      
      private GridDataProvider dataProvider;
      
      public final GridDataProvider dataProvider() {
        return this.dataProvider;
      }
      
      private EnvironmentClock.Component clock;
      
      private EnvironmentClock implem_clock;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_clock implements EnvironmentClock.Requires {
        public final ActionHandler actionHandler() {
          return Ecosystem.Environment.ComponentImpl.this.bridge.robotActionHandler();
        }
        
        public final EnvironmentRenderer renderEnvironment() {
          return Ecosystem.Environment.ComponentImpl.this.bridge.guiRenderEnvironment();
        }
      }
      
      
      public final EnvironmentClock.Component clock() {
        return this.clock;
      }
      
      private EnvironmentMove.Component move;
      
      private EnvironmentMove implem_move;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_move implements EnvironmentMove.Requires {
        public final GridUpdater updateGrid() {
          return Ecosystem.Environment.ComponentImpl.this.gridManager.updateGrid();
        }
      }
      
      
      public final EnvironmentMove.Component move() {
        return this.move;
      }
      
      private GridManager.Component gridManager;
      
      private GridManager implem_gridManager;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_gridManager implements GridManager.Requires {
      }
      
      
      public final GridManager.Component gridManager() {
        return this.gridManager;
      }
    }
    
    
    /**
     * Used to check that two components are not created from the same implementation,
     * that the component has been started to call requires(), provides() and parts()
     * and that the component is not started by hand.
     * 
     */
    private boolean init = false;;
    
    /**
     * Used to check that the component is not started by hand.
     */
    private boolean started = false;;
    
    private Ecosystem.Environment.ComponentImpl selfComponent;
    
    /**
     * Can be overridden by the implementation.
     * It will be called automatically after the component has been instantiated.
     * 
     */
    protected void start() {
      if (!this.init || this.started) {
      	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
      }
      
    }
    
    /**
     * This can be called by the implementation to access the provided ports.
     * 
     */
    protected Ecosystem.Environment.Provides provides() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract GridDataProvider make_dataProvider();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected Ecosystem.Environment.Requires requires() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
      }
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected Ecosystem.Environment.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract EnvironmentClock make_clock();
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract EnvironmentMove make_move();
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract GridManager make_gridManager();
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized Ecosystem.Environment.Component _newComponent(final Ecosystem.Environment.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Environment has already been used to create a component, use another one.");
      }
      this.init = true;
      Ecosystem.Environment.ComponentImpl comp = new Ecosystem.Environment.ComponentImpl(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private Ecosystem.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Ecosystem.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Ecosystem.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Ecosystem.Parts eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class GUI {
    @SuppressWarnings("all")
    public interface Requires {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public EnvironmentUpdater gridUpdateEnvironment();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public GridDataProvider gridDataProvider();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public EnvironmentRenderer renderEnvironment();
    }
    
    
    @SuppressWarnings("all")
    public interface Component extends Ecosystem.GUI.Provides {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public EnvironmentGUI.Component environmentGui();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public AppGUI.Component appGui();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements Ecosystem.GUI.Component, Ecosystem.GUI.Parts {
      private final Ecosystem.GUI.Requires bridge;
      
      private final Ecosystem.GUI implementation;
      
      public void start() {
        assert this.environmentGui != null: "This is a bug.";
        ((EnvironmentGUI.ComponentImpl) this.environmentGui).start();
        assert this.appGui != null: "This is a bug.";
        ((AppGUI.ComponentImpl) this.appGui).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.environmentGui == null: "This is a bug.";
        assert this.implem_environmentGui == null: "This is a bug.";
        this.implem_environmentGui = this.implementation.make_environmentGui();
        if (this.implem_environmentGui == null) {
        	throw new RuntimeException("make_environmentGui() in DiezeFond.Ecosystem$GUI should not return null.");
        }
        this.environmentGui = this.implem_environmentGui._newComponent(new BridgeImpl_environmentGui(), false);
        assert this.appGui == null: "This is a bug.";
        assert this.implem_appGui == null: "This is a bug.";
        this.implem_appGui = this.implementation.make_appGui();
        if (this.implem_appGui == null) {
        	throw new RuntimeException("make_appGui() in DiezeFond.Ecosystem$GUI should not return null.");
        }
        this.appGui = this.implem_appGui._newComponent(new BridgeImpl_appGui(), false);
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final Ecosystem.GUI implem, final Ecosystem.GUI.Requires b, final boolean doInits) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null: "This is a bug.";
        implem.selfComponent = this;
        
        // prevent them to be called twice if we are in
        // a specialized component: only the last of the
        // hierarchy will call them after everything is initialised
        if (doInits) {
        	initParts();
        	initProvidedPorts();
        }
        
      }
      
      public final EnvironmentRenderer renderEnvironment() {
        return this.environmentGui.renderEnvironment();
      }
      
      private EnvironmentGUI.Component environmentGui;
      
      private EnvironmentGUI implem_environmentGui;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_environmentGui implements EnvironmentGUI.Requires {
        public final GridDataProvider dataProvider() {
          return Ecosystem.GUI.ComponentImpl.this.bridge.gridDataProvider();
        }
      }
      
      
      public final EnvironmentGUI.Component environmentGui() {
        return this.environmentGui;
      }
      
      private AppGUI.Component appGui;
      
      private AppGUI implem_appGui;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_appGui implements AppGUI.Requires {
        public final EnvironmentUpdater updateEnvironment() {
          return Ecosystem.GUI.ComponentImpl.this.bridge.gridUpdateEnvironment();
        }
      }
      
      
      public final AppGUI.Component appGui() {
        return this.appGui;
      }
    }
    
    
    /**
     * Used to check that two components are not created from the same implementation,
     * that the component has been started to call requires(), provides() and parts()
     * and that the component is not started by hand.
     * 
     */
    private boolean init = false;;
    
    /**
     * Used to check that the component is not started by hand.
     */
    private boolean started = false;;
    
    private Ecosystem.GUI.ComponentImpl selfComponent;
    
    /**
     * Can be overridden by the implementation.
     * It will be called automatically after the component has been instantiated.
     * 
     */
    protected void start() {
      if (!this.init || this.started) {
      	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
      }
      
    }
    
    /**
     * This can be called by the implementation to access the provided ports.
     * 
     */
    protected Ecosystem.GUI.Provides provides() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected Ecosystem.GUI.Requires requires() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
      }
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected Ecosystem.GUI.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract EnvironmentGUI make_environmentGui();
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract AppGUI make_appGui();
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized Ecosystem.GUI.Component _newComponent(final Ecosystem.GUI.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of GUI has already been used to create a component, use another one.");
      }
      this.init = true;
      Ecosystem.GUI.ComponentImpl comp = new Ecosystem.GUI.ComponentImpl(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private Ecosystem.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Ecosystem.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Ecosystem.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Ecosystem.Parts eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
  }
  
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   */
  private boolean started = false;;
  
  private Ecosystem.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
    
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected Ecosystem.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Ecosystem.Requires requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected Ecosystem.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized Ecosystem.Component _newComponent(final Ecosystem.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Ecosystem has already been used to create a component, use another one.");
    }
    this.init = true;
    Ecosystem.ComponentImpl comp = new Ecosystem.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Ecosystem.Robot make_Robot();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Ecosystem.Robot _createImplementationOfRobot() {
    Ecosystem.Robot implem = make_Robot();
    if (implem == null) {
    	throw new RuntimeException("make_Robot() in DiezeFond.Ecosystem should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Ecosystem.Environment make_Environment();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Ecosystem.Environment _createImplementationOfEnvironment() {
    Ecosystem.Environment implem = make_Environment();
    if (implem == null) {
    	throw new RuntimeException("make_Environment() in DiezeFond.Ecosystem should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Ecosystem.GUI make_GUI();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Ecosystem.GUI _createImplementationOfGUI() {
    Ecosystem.GUI implem = make_GUI();
    if (implem == null) {
    	throw new RuntimeException("make_GUI() in DiezeFond.Ecosystem should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Ecosystem.Component newComponent() {
    return this._newComponent(new Ecosystem.Requires() {}, true);
  }
}
