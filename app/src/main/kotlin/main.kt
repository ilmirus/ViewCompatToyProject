package us.ilmir.viewcompattoyproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.MotionEventCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View

class Hello @JvmOverloads constructor(
        ctx: Context,
        attrs: AttributeSet? = null
) : View(ctx, attrs) {
    var showText = false
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }
    var textX = 0.0f
    var textY = 0.0f
    var textWidth = 0.0f
    var textHeight = 0.0f
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        // Load properties
        val a = ctx.theme.obtainStyledAttributes(attrs, R.styleable.Hello, 0, 0)
        try {
            showText = a.getBoolean(R.styleable.Hello_showText, false)
            textX = a.getDimension(R.styleable.Hello_textX, 0.0f)
            textY = a.getDimension(R.styleable.Hello_textY, 0.0f)
            textHeight = a.getDimension(R.styleable.Hello_textHeight, 0.0f)
            textWidth = a.getDimension(R.styleable.Hello_textWidth, 0.0f)
        } finally {
            a.recycle()
        }

        // Set software rendering
        if (!isInEditMode) {
            ViewCompat.setLayerType(this, View.LAYER_TYPE_SOFTWARE, null)
        }

        textPaint.color = Color.BLACK
        if (textHeight.toInt() == 0) {
            textHeight = textPaint.textSize
        } else {
            textPaint.textSize = textHeight
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawText("Hello elevation: " + ViewCompat.getElevation(this), textX, textY, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = MotionEventCompat.getActionMasked(event)
        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("ViewCompatToyProject", "ACTION_DOWN")
                ViewCompat.setAlpha(this, if (showText) 1f else 0f)
                ViewCompat.animate(this).alpha(if (showText) 0f else 1f).setDuration(1000)
                showText = !showText
                true
            }
            else -> super.onTouchEvent(event)
        }
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
}
