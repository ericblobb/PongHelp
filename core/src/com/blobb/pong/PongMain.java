package com.blobb.pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

public class PongMain extends ApplicationAdapter {
	
	private Rectangle playerPaddle;
	private Rectangle aiPaddle;
	private Rectangle ball;
	
	private Rectangle topWall;
	private Rectangle bottomWall;
	private Rectangle rightWall;
	private Rectangle leftWall;
	
	private ShapeRenderer sr;
	private OrthographicCamera camera;
	
	public final int WIDTH = 400;
	public final int HEIGHT = WIDTH / 4 * 3;
	
	private int ballSpeed = 100;
	private int paddleSpeed = 100;
	
	private int playerScore = 0;
	private int aiScore = 0;
	
	private boolean isGoingUp = false;
	private boolean isGoingDown = false;
	private boolean isGoingLeft = false;
	private boolean isGoingRight = false;
	
	private float chanceForBallY = MathUtils.random(10f);
	private float chanceForBallX = MathUtils.random(10f);
	
	@Override
	public void create () {
		rightWall = new Rectangle(0 - 1, 0, 1, HEIGHT);
		leftWall = new Rectangle(WIDTH + 1, 0, 1, HEIGHT);
		topWall = new Rectangle(0, HEIGHT + 1, WIDTH, 1);
		bottomWall = new Rectangle(0, 0 + 1, WIDTH, 1);
		
		playerPaddle = new Rectangle(10, HEIGHT / 2 - 25, 10, 75);
		aiPaddle = new Rectangle(WIDTH - 20, HEIGHT / 2 - 25, 10, 75);
		
		ball = new Rectangle(WIDTH / 2 - 5, HEIGHT / 2 - 5, 10, 10);
		
		sr = new ShapeRenderer();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		sr.setProjectionMatrix(camera.combined);
		
		//renders everything
		sr.begin(ShapeType.Filled);
		sr.rect(playerPaddle.x, playerPaddle.y, playerPaddle.width, playerPaddle.height);
		sr.rect(aiPaddle.x, aiPaddle.y, aiPaddle.width, aiPaddle.height);
		sr.rect(ball.x, ball.y, ball.width, ball.height);
		sr.end();
		
		figureOutDirection();
		
		startMovingBall();
		
		if (ball.overlaps(bottomWall) || ball.overlaps(topWall)) {
			bounceWall();
		}
	}
	
	private void startMovingBall() {
		
		if (chanceForBallX < 5) isGoingRight = true;
		if (chanceForBallX > 5) isGoingLeft = true;
		if (chanceForBallY < 5) isGoingUp = true;
		if (chanceForBallY > 5) isGoingDown = true;
	}
		
	private void reset() {
		playerPaddle.x = 10;
		playerPaddle.y = HEIGHT / 2 - 25;
		
		aiPaddle.x = WIDTH - 20;
		aiPaddle.y = HEIGHT / 2 - 25;
		
		ball.x = WIDTH / 2 - 5;
		ball.y = HEIGHT / 2 - 5;
		
		chanceForBallY = MathUtils.random(10f);
		chanceForBallX = MathUtils.random(10f);
		
		startMovingBall();
	}
	
	private void figureOutDirection() {
		if (isGoingRight && !isGoingLeft) ball.x += ballSpeed * Gdx.graphics.getDeltaTime();
		else if (isGoingLeft && !isGoingRight) ball.x -= ballSpeed * Gdx.graphics.getDeltaTime();
		
		if (isGoingUp && !isGoingDown) ball.y += ballSpeed * Gdx.graphics.getDeltaTime();
		else if (isGoingDown && !isGoingUp) ball.y -= ballSpeed * Gdx.graphics.getDeltaTime();
	}
	
	private void bounceWall() {
		//bottom wall collision
		if (isGoingRight && isGoingDown) {
			isGoingDown = false;
			isGoingUp = true;
			figureOutDirection();
		}
		else if (isGoingLeft && isGoingDown) {
			isGoingDown = false;
			isGoingUp = true;
			figureOutDirection();
		}
		
		//top wall collision
		if (isGoingRight && isGoingUp) {
			isGoingUp = false;
			isGoingDown = true;
			figureOutDirection();
		}
		else if (isGoingLeft && isGoingUp) {
			isGoingUp = false;
			isGoingDown = true;
			figureOutDirection();
		}
	}
	
}
