package game;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.jbox2d.common.Vec2;
/*
         Listens for key presses and releases to control the robot's movement

         WASD controls:
         A=left
         D=right
         W=jump
 */

public class Controller extends KeyAdapter{
    private Robot tee;
    public Controller(Robot tee){
        this.tee = tee;}

    @Override
    public void keyPressed(KeyEvent e){ //called when a key is pressed
        int code= e.getKeyCode(); // variable code will store the code of the pressed key, we can use this to determine which key was pressed and respond accordingly

        if (code== KeyEvent.VK_A){ //when its A
            tee.setLinearVelocity(new Vec2(-5,tee.getLinearVelocity().y)); //-5 will move it left
            tee.faceLeft(); //calling the faceleft image method
        }
        else if (code == KeyEvent.VK_D){ //same for D but positive 5 to move right
            tee.setLinearVelocity(new Vec2(5,tee.getLinearVelocity().y));
            tee.faceRight();
        }
        else if (code == KeyEvent.VK_W){

            if (Math.abs(tee.getLinearVelocity().y)< 2f) {
                tee.setLinearVelocity(new Vec2(tee.getLinearVelocity().x,12f));
                tee.showJump();
                tee.getGame().playJumpSound(); // jump sound effect
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e){ // on release, stop horizontal movement and restore the idle sprite
        int code= e.getKeyCode();
        if (code== KeyEvent.VK_A){ //when A is released, stop horizontal movement and restore idle sprite
            tee.setLinearVelocity(new Vec2(0, tee.getLinearVelocity().y));
            tee.land(); // restore idle sprite on landing
        }
        else if (code == KeyEvent.VK_D){
            tee.setLinearVelocity(new Vec2(0, tee.getLinearVelocity().y));
            tee.land();
        }
    }
}