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
import fr.sma.speadl.GuiConnector;
import fr.sma.speadl.MoveHandler;
import java.util.logging.MemoryHandler;

@SuppressWarnings("all")
public abstract class EcoEnvironment {
  @SuppressWarnings("all")
  public interface Requires {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ActionHandler actionHandler();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends EcoEnvironment.Provides {
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
  public static class ComponentImpl implements EcoEnvironment.Component, EcoEnvironment.Parts {
    private final EcoEnvironment.Requires bridge;
    
    private final EcoEnvironment implementation;
    
    public void start() {
      assert this.clock != null: "This is a bug.";
      ((EnvironmentClock.ComponentImpl) this.clock).start();
      assert this.move != null: "This is a bug.";
      ((EnvironmentMove.ComponentImpl) this.move).start();
      assert this.gridManager != null: "This is a bug.";
      ((GridManager.ComponentImpl) this.gridManager).start();
      assert this.environmentGui != null: "This is a bug.";
      ((EnvironmentGUI.ComponentImpl) this.environmentGui).start();
      assert this.appGui != null: "This is a bug.";
      ((AppGUI.ComponentImpl) this.appGui).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.clock == null: "This is a bug.";
      assert this.implem_clock == null: "This is a bug.";
      this.implem_clock = this.implementation.make_clock();
      if (this.implem_clock == null) {
      	throw new RuntimeException("make_clock() in DiezeFond.EcoEnvironment should not return null.");
      }
      this.clock = this.implem_clock._newComponent(new BridgeImpl_clock(), false);
      assert this.move == null: "This is a bug.";
      assert this.implem_move == null: "This is a bug.";
      this.implem_move = this.implementation.make_move();
      if (this.implem_move == null) {
      	throw new RuntimeException("make_move() in DiezeFond.EcoEnvironment should not return null.");
      }
      this.move = this.implem_move._newComponent(new BridgeImpl_move(), false);
      assert this.gridManager == null: "This is a bug.";
      assert this.implem_gridManager == null: "This is a bug.";
      this.implem_gridManager = this.implementation.make_gridManager();
      if (this.implem_gridManager == null) {
      	throw new RuntimeException("make_gridManager() in DiezeFond.EcoEnvironment should not return null.");
      }
      this.gridManager = this.implem_gridManager._newComponent(new BridgeImpl_gridManager(), false);
      assert this.environmentGui == null: "This is a bug.";
      assert this.implem_environmentGui == null: "This is a bug.";
      this.implem_environmentGui = this.implementation.make_environmentGui();
      if (this.implem_environmentGui == null) {
      	throw new RuntimeException("make_environmentGui() in DiezeFond.EcoEnvironment should not return null.");
      }
      this.environmentGui = this.implem_environmentGui._newComponent(new BridgeImpl_environmentGui(), false);
      assert this.appGui == null: "This is a bug.";
      assert this.implem_appGui == null: "This is a bug.";
      this.implem_appGui = this.implementation.make_appGui();
      if (this.implem_appGui == null) {
      	throw new RuntimeException("make_appGui() in DiezeFond.EcoEnvironment should not return null.");
      }
      this.appGui = this.implem_appGui._newComponent(new BridgeImpl_appGui(), false);
      
    }
    
    protected void initProvidedPorts() {
      assert this.actionHandler == null: "This is a bug.";
      this.actionHandler = this.implementation.make_actionHandler();
      if (this.actionHandler == null) {
      	throw new RuntimeException("make_actionHandler() in DiezeFond.EcoEnvironment should not return null.");
      }
      
    }
    
    public ComponentImpl(final EcoEnvironment implem, final EcoEnvironment.Requires b, final boolean doInits) {
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
    
    private ActionHandler actionHandler;
    
    public final ActionHandler actionHandler() {
      return this.actionHandler;
    }
    
    private EnvironmentClock.Component clock;
    
    private EnvironmentClock implem_clock;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_clock implements EnvironmentClock.Requires {
      public final ActionHandler actionHandler() {
        return EcoEnvironment.ComponentImpl.this.actionHandler();
      }
      
      public final EnvironmentRenderer renderEnvironment() {
        return EcoEnvironment.ComponentImpl.this.environmentGui.renderEnvironment();
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
        return EcoEnvironment.ComponentImpl.this.gridManager.updateGrid();
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
    
    private EnvironmentGUI.Component environmentGui;
    
    private EnvironmentGUI implem_environmentGui;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_environmentGui implements EnvironmentGUI.Requires {
      public final GridDataProvider dataProvider() {
        return EcoEnvironment.ComponentImpl.this.gridManager.dataProvider();
      }
      
      public final GuiConnector guiLink() {
        return EcoEnvironment.ComponentImpl.this.appGui.guiLink();
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
        return EcoEnvironment.ComponentImpl.this.gridManager.updateEnvironment();
      }
    }
    
    
    public final AppGUI.Component appGui() {
      return this.appGui;
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
    public interface Component extends EcoEnvironment.Robot.Provides {
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
    public static class ComponentImpl implements EcoEnvironment.Robot.Component, EcoEnvironment.Robot.Parts {
      private final EcoEnvironment.Robot.Requires bridge;
      
      private final EcoEnvironment.Robot implementation;
      
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
        	throw new RuntimeException("make_memory() in DiezeFond.EcoEnvironment$Robot should not return null.");
        }
        this.memory = this.implem_memory._newComponent(new BridgeImpl_memory(), false);
        assert this.actionManager == null: "This is a bug.";
        assert this.implem_actionManager == null: "This is a bug.";
        this.implem_actionManager = this.implementation.make_actionManager();
        if (this.implem_actionManager == null) {
        	throw new RuntimeException("make_actionManager() in DiezeFond.EcoEnvironment$Robot should not return null.");
        }
        this.actionManager = this.implem_actionManager._newComponent(new BridgeImpl_actionManager(), false);
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final EcoEnvironment.Robot implem, final EcoEnvironment.Robot.Requires b, final boolean doInits) {
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
          return EcoEnvironment.Robot.ComponentImpl.this.memory.memoryHandler();
        }
        
        public final MoveHandler moveHandler() {
          return EcoEnvironment.Robot.ComponentImpl.this.bridge.environmentMoveHandler();
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
    
    private EcoEnvironment.Robot.ComponentImpl selfComponent;
    
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
    protected EcoEnvironment.Robot.Provides provides() {
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
    protected EcoEnvironment.Robot.Requires requires() {
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
    protected EcoEnvironment.Robot.Parts parts() {
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
    public synchronized EcoEnvironment.Robot.Component _newComponent(final EcoEnvironment.Robot.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Robot has already been used to create a component, use another one.");
      }
      this.init = true;
      EcoEnvironment.Robot.ComponentImpl comp = new EcoEnvironment.Robot.ComponentImpl(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private EcoEnvironment.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected EcoEnvironment.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected EcoEnvironment.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected EcoEnvironment.Parts eco_parts() {
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
  
  private EcoEnvironment.ComponentImpl selfComponent;
  
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
  protected EcoEnvironment.Provides provides() {
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
  protected abstract ActionHandler make_actionHandler();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected EcoEnvironment.Requires requires() {
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
  protected EcoEnvironment.Parts parts() {
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
  public synchronized EcoEnvironment.Component _newComponent(final EcoEnvironment.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of EcoEnvironment has already been used to create a component, use another one.");
    }
    this.init = true;
    EcoEnvironment.ComponentImpl comp = new EcoEnvironment.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract EcoEnvironment.Robot make_Robot();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public EcoEnvironment.Robot _createImplementationOfRobot() {
    EcoEnvironment.Robot implem = make_Robot();
    if (implem == null) {
    	throw new RuntimeException("make_Robot() in DiezeFond.EcoEnvironment should not return null.");
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
  public EcoEnvironment.Component newComponent() {
    return this._newComponent(new EcoEnvironment.Requires() {}, true);
  }
}
