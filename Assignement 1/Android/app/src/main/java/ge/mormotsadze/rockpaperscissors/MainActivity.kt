package ge.mormotsadze.rockpaperscissors

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlin.math.log
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    /* Buttons */
    private lateinit var rockButton: Button
    private lateinit var paperButton: Button
    private lateinit var scissorsButton: Button

    /* ImageViews */
    private lateinit var computerImage: ImageView
    private lateinit var playerImage: ImageView

    /* TextViews*/
    private lateinit var greetingTextView: TextView
    private lateinit var computerScoreTextView: TextView
    private lateinit var playerScoreTextView: TextView

    /* Integers */
    private var computerScore = 0
    private var playerScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        initButtonViews()
        initImageViews()
        initTextViews()
    }

    private fun initButtonViews(){
        initRockButton()
        initPaperButton()
        initScissorButton()
    }

    private fun initRockButton() {
        rockButton = findViewById<Button>(R.id.rock_button)

        rockButton.setOnClickListener {
            hideGreetingTextView()
            setComputerImage()
            setUpScore(0)
            Log.i("Button", "Rock Button pressed")
        }
    }

    private fun initScissorButton() {
        scissorsButton = findViewById<Button>(R.id.scissors_button)

        scissorsButton.setOnClickListener {
            hideGreetingTextView()
            setComputerImage()
            setUpScore(1)
            Log.i("Button", "Scissors Button pressed")
        }
    }

    private fun initPaperButton() {
        paperButton = findViewById<Button>(R.id.paper_button)

        paperButton.setOnClickListener {
            hideGreetingTextView()
            setComputerImage()
            setUpScore(2)
            Log.i("Button", "Paper Button pressed")
        }
    }

    private fun initImageViews(){
        computerImage = findViewById<ImageView>(R.id.computer_image)
        playerImage = findViewById<ImageView>(R.id.player_image)

        computerImage.visibility = View.GONE
        playerImage.visibility = View.GONE
    }

    private fun initTextViews(){
        greetingTextView = findViewById<TextView>(R.id.greeting_label)
        computerScoreTextView = findViewById<TextView>(R.id.computer_score)
        playerScoreTextView = findViewById<TextView>(R.id.player_score)
    }

    private fun hideGreetingTextView(){
        greetingTextView.visibility = View.GONE
    }

    private fun setComputerImage(): Int{
        computerImage.visibility = View.VISIBLE
        var random = getRandomIndx()
        computerImage.setImageResource(getImageResource(random))
        return random
    }

    private fun setPlayerImage(indx: Int){
        playerImage.visibility = View.VISIBLE
        playerImage.setImageResource(getImageResource(indx));
    }

    private fun setUpScore(playerScore: Int){
        var random = setComputerImage()
        setPlayerImage(playerScore)
        setScore(random, playerScore)
    }

    private fun setScore(computerScoreToSet: Int, playerScoretoSet: Int){
        if((computerScoreToSet + 1) % 3 == playerScoretoSet){ computerWon() }
        else if(computerScoreToSet == playerScoretoSet){ draw() }
        else { playerWon() }
    }

    @SuppressLint("ResourceAsColor")
    private fun computerWon(){
        computerScore += 1
        computerScoreTextView.text = computerScore.toString()
        computerScoreTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.green))
        playerScoreTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
    }

    @SuppressLint("ResourceAsColor")
    private fun playerWon(){
        playerScore += 1
        playerScoreTextView.text = playerScore.toString()
        playerScoreTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.green))
        computerScoreTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
    }

    @SuppressLint("ResourceAsColor")
    private fun draw(){
        playerScoreTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.yellow))
        computerScoreTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.yellow))
    }

    private fun getRandomIndx(): Int{
        var random = Random.nextInt(0, 3)
        return random
    }

    private fun getImageResource(n: Int): Int{
        if (n == 0){ return R.drawable.rock }
        else if(n == 1){ return R.drawable.scissors }
        return R.drawable.paper
    }

}