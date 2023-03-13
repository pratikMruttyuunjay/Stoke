package com.mruttyuunjay.stoke.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mruttyuunjay.stoke.R

class KtPasscodeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs), View.OnClickListener {
    private var secondInput = false
    private var localPasscode = ""
     var listener: PasscodeViewListener? = null
    private var layoutPsd: ViewGroup? = null
    private var tvInputTip: TextView? = null
    private var number0: TextView? = null
    private var number1: TextView? = null
    private var number2: TextView? = null
    private var number3: TextView? = null
    private var number4: TextView? = null
    private var number5: TextView? = null
    private var number6: TextView? = null
    private var number7: TextView? = null
    private var number8: TextView? = null
    private var number9: TextView? = null
    private var numberB: ImageView? = null
    private var numberOK: ImageView? = null
    private var ivLock: ImageView? = null
    private var ivOk: ImageView? = null
    private var cursor: View? = null
    private var firstInputTip: String? = "Enter a passcode of 4 digits"

    private var secondInputTip: String? = "Re-enter new passcode"

    private var wrongLengthTip: String? = "Enter a passcode of 4 digits"

    private var wrongInputTip: String? = "Passcode do not match"

    private var correctInputTip: String? = "Passcode is correct"

    private var passcodeLength = 4

    private var correctStatusColor = -0x9e3aa0 //0xFFFF0000

    private var wrongStatusColor = -0xdbfab

    private var normalStatusColor = -0x1

    private var numberTextColor = -0x8b8b8c


    @get:PasscodeViewType
    var passcodeType = PasscodeViewType.TYPE_SET_PASSCODE
        private set

    init {
        inflate(getContext(), R.layout.layout_passcode_view, this)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasscodeView)
        try {
            passcodeType =
                typedArray.getInt(R.styleable.PasscodeView_passcodeViewType, passcodeType)
            passcodeLength =
                typedArray.getInt(R.styleable.PasscodeView_passcodeLength, passcodeLength)
            normalStatusColor =
                typedArray.getColor(R.styleable.PasscodeView_normalStateColor, normalStatusColor)
            wrongStatusColor =
                typedArray.getColor(R.styleable.PasscodeView_wrongStateColor, wrongStatusColor)
            correctStatusColor =
                typedArray.getColor(R.styleable.PasscodeView_correctStateColor, correctStatusColor)
            numberTextColor =
                typedArray.getColor(R.styleable.PasscodeView_numberTextColor, numberTextColor)
            firstInputTip = typedArray.getString(R.styleable.PasscodeView_firstInputTip)
            secondInputTip = typedArray.getString(R.styleable.PasscodeView_secondInputTip)
            wrongLengthTip = typedArray.getString(R.styleable.PasscodeView_wrongLengthTip)
            wrongInputTip = typedArray.getString(R.styleable.PasscodeView_wrongInputTip)
            correctInputTip = typedArray.getString(R.styleable.PasscodeView_correctInputTip)


        } finally {
            typedArray.recycle()
        }
        firstInputTip = if (firstInputTip == null) "Enter a passcode of 4 digits" else firstInputTip
        secondInputTip = if (secondInputTip == null) "Re-enter new passcode" else secondInputTip
        wrongLengthTip = if (wrongLengthTip == null) firstInputTip else wrongLengthTip
        wrongInputTip = if (wrongInputTip == null) "Passcode do not match" else wrongInputTip
        correctInputTip = if (correctInputTip == null) "Passcode is correct" else correctInputTip
        init()
    }

    private fun init() {
        layoutPsd = findViewById<View>(R.id.layout_psd) as ViewGroup
        tvInputTip = findViewById<View>(R.id.tv_input_tip) as TextView
        cursor = findViewById(R.id.cursor)
        ivLock = findViewById<View>(R.id.iv_lock) as ImageView
        ivOk = findViewById<View>(R.id.iv_ok) as ImageView
        tvInputTip!!.text = firstInputTip
        number0 = findViewById<View>(R.id.number0) as TextView
        number1 = findViewById<View>(R.id.number1) as TextView
        number2 = findViewById<View>(R.id.number2) as TextView
        number3 = findViewById<View>(R.id.number3) as TextView
        number4 = findViewById<View>(R.id.number4) as TextView
        number5 = findViewById<View>(R.id.number5) as TextView
        number6 = findViewById<View>(R.id.number6) as TextView
        number7 = findViewById<View>(R.id.number7) as TextView
        number8 = findViewById<View>(R.id.number8) as TextView
        number9 = findViewById<View>(R.id.number9) as TextView
//        numberOK = findViewById<View>(R.id.numberOK) as ImageView
        numberB = findViewById<View>(R.id.numberB) as ImageView
        number0!!.setOnClickListener(this)
        number1!!.setOnClickListener(this)
        number2!!.setOnClickListener(this)
        number3!!.setOnClickListener(this)
        number4!!.setOnClickListener(this)
        number5!!.setOnClickListener(this)
        number6!!.setOnClickListener(this)
        number7!!.setOnClickListener(this)
        number8!!.setOnClickListener(this)
        number9!!.setOnClickListener(this)
        numberB!!.setOnClickListener { deleteChar() }
        numberB!!.setOnLongClickListener {
            deleteAllChars()
            true
        }
//        numberOK!!.setOnClickListener { next() }
        tintImageView(ivLock, numberTextColor)
        tintImageView(numberB, numberTextColor)
//        tintImageView(numberOK, numberTextColor)
        tintImageView(ivOk, correctStatusColor)
        number0!!.tag = 0
        number1!!.tag = 1
        number2!!.tag = 2
        number3!!.tag = 3
        number4!!.tag = 4
        number5!!.tag = 5
        number6!!.tag = 6
        number7!!.tag = 7
        number8!!.tag = 8
        number9!!.tag = 9
        number0!!.setTextColor(numberTextColor)
        number1!!.setTextColor(numberTextColor)
        number2!!.setTextColor(numberTextColor)
        number3!!.setTextColor(numberTextColor)
        number4!!.setTextColor(numberTextColor)
        number5!!.setTextColor(numberTextColor)
        number6!!.setTextColor(numberTextColor)
        number7!!.setTextColor(numberTextColor)
        number8!!.setTextColor(numberTextColor)
        number9!!.setTextColor(numberTextColor)
    }

    override fun onClick(view: View) {
        val number = view.tag as Int

       val list = ArrayList<Int>()
        list.add(number)

//        Log.wtf("num: Size1",list.indices.toString())
//        added5Digits(list)
        addChar(number)
//        if (list.size == passcodeLength) next()
    }

    /**
     * set  localPasscode
     *
     * @param localPasscode the code will to check
     */
    fun setLocalPasscode(localPasscode: String): KtPasscodeView {
        for (i in 0 until localPasscode.length) {
            val c = localPasscode[i]
            if (c < '0' || c > '9') {
                throw RuntimeException("must be number digit")
            }
        }
        this.localPasscode = localPasscode
        passcodeType = PasscodeViewType.TYPE_CHECK_PASSCODE
        return this
    }

    fun setListener(listener: PasscodeViewListener?): KtPasscodeView {
        this.listener = listener
        return this
    }

    fun setFirstInputTip(firstInputTip: String?): KtPasscodeView {
        this.firstInputTip = firstInputTip
        return this
    }

    fun setSecondInputTip(secondInputTip: String?): KtPasscodeView {
        this.secondInputTip = secondInputTip
        return this
    }

    fun setWrongLengthTip(wrongLengthTip: String?): KtPasscodeView {
        this.wrongLengthTip = wrongLengthTip
        return this
    }

    fun setWrongInputTip(wrongInputTip: String?): KtPasscodeView {
        this.wrongInputTip = wrongInputTip
        return this
    }

    fun setCorrectInputTip(correctInputTip: String?): KtPasscodeView {
        this.correctInputTip = correctInputTip
        return this
    }

    fun setPasscodeLength(passcodeLength: Int): KtPasscodeView {
        this.passcodeLength = passcodeLength
        return this
    }

    fun setCorrectStatusColor(correctStatusColor: Int): KtPasscodeView {
        this.correctStatusColor = correctStatusColor
        return this
    }

    fun setWrongStatusColor(wrongStatusColor: Int): KtPasscodeView {
        this.wrongStatusColor = wrongStatusColor
        return this
    }

    fun setNormalStatusColor(normalStatusColor: Int): KtPasscodeView {
        this.normalStatusColor = normalStatusColor
        return this
    }

    fun setNumberTextColor(numberTextColor: Int): KtPasscodeView {
        this.numberTextColor = numberTextColor
        return this
    }

    fun setPasscodeType(@PasscodeViewType passcodeType: Int): KtPasscodeView {
        this.passcodeType = passcodeType
        return this
    }

    /**
     * <pre>
     * passcodeView.setListener(new PasscodeView.PasscodeViewListener() {
     * public void onFail() {
     * }
     *
     * public void onSuccess(String number) {
     * String encrypted = SecurePreferences.hashPrefKey(raw);
     * SharedPreferences.Editor editor = keys.edit();
     * editor.putString("passcode", encrypted);
     * editor.commit();
     * finish();
     * }
     * });
     * Second, compare using the overridden equals() method:
     *
     * class PView extends PasscodeView {
     * public PView(Context context) {
     * super(context);
     * }
     * protected boolean equals(String psd) {
     * String after = SecurePreferences.hashPrefKey(raw);
     * return after.equals(encrypted_passcode);
     * }
     * }
     * PView passcodeView = new PView(PasscodeActivity.this);
     *
    </pre> *
     * @param value the input number string
     * @return true if val is right passcode
     */
    private fun equal(value: String): Boolean {
        return localPasscode == value
    }

    private operator fun next() {

        if (passcodeType == PasscodeViewType.TYPE_CHECK_PASSCODE && TextUtils.isEmpty(localPasscode)) {
            throw RuntimeException("must set localPasscode when type is TYPE_CHECK_PASSCODE")
        }

        val psd = passcodeFromView
        if (psd.length != passcodeLength) {
            tvInputTip!!.text = wrongLengthTip
            runTipTextAnimation()
            return
        }

        if (passcodeType == PasscodeViewType.TYPE_SET_PASSCODE && !secondInput) {
            // second input
            tvInputTip!!.text = secondInputTip
            localPasscode = psd
            clearChar()
            secondInput = true
            return
        }
        if (equal(psd)) {
            // match
            runOkAnimation()
        } else {
            runWrongAnimation()
        }
    }

    private fun addChar(number: Int) {
        if (layoutPsd!!.childCount >= passcodeLength) {
            return
        }
        val psdView = CircleView(context)
        val size = dpToPx(8f)
        val params = LinearLayout.LayoutParams(size, size)
        params.setMargins(size, 0, size, 0)
        psdView.layoutParams = params
        psdView.color = normalStatusColor
        psdView.tag = number
        layoutPsd!!.addView(psdView)

//        Log.wtf("childCount: Size",layoutPsd!!.childCount.toString())

        if (layoutPsd!!.childCount == passcodeLength) next()
    }

    private fun dpToPx(valueInDp: Float): Int {
        val metrics = resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics).toInt()
    }

    private fun tintImageView(imageView: ImageView?, color: Int) {
        imageView!!.drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    fun clearChar() {
        layoutPsd!!.removeAllViews()
    }

    private fun deleteChar() {
        val childCount = layoutPsd!!.childCount
        if (childCount <= 0) {
            return
        }
        layoutPsd!!.removeViewAt(childCount - 1)
    }

    private fun deleteAllChars() {
//        Log.d("deleteAllChars","deleteAllChars")
        val childCount = layoutPsd!!.childCount
        if (childCount <= 0) {
            return
        }
        layoutPsd!!.removeAllViews()
    }

    fun runTipTextAnimation() {
        shakeAnimator(tvInputTip).start()
    }

    fun runWrongAnimation() {
//        Log.wtf("#KtPasscode","runWrongCalled1")
        cursor!!.translationX = 0f
        cursor!!.visibility = VISIBLE
        cursor!!.animate()
            .translationX(layoutPsd!!.width.toFloat())
            .setDuration(600)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    cursor!!.visibility = INVISIBLE
                    tvInputTip!!.text = wrongInputTip
                    setPSDViewBackgroundResource(wrongStatusColor)
                    val animator = shakeAnimator(layoutPsd)
                    animator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            setPSDViewBackgroundResource(normalStatusColor)
                            if (secondInput && listener != null) {
                                listener!!.onFail(passcodeFromView)
                                clearChar()
                            }else if (passcodeType == PasscodeViewType.TYPE_CHECK_PASSCODE && listener != null){
                                clearChar()
                                listener!!.onFail(passcodeFromView)
                            }
                        }
                    })
                    animator.start()
                }
            })
            .start()
    }

    private fun shakeAnimator(view: View?): Animator {
        return ObjectAnimator
            .ofFloat(view, "translationX", 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
            .setDuration(500)
    }

    private fun setPSDViewBackgroundResource(color: Int) {
        val childCount = layoutPsd!!.childCount
        for (i in 0 until childCount) {
            (layoutPsd!!.getChildAt(i) as CircleView).color = color
        }
    }

    fun runOkAnimation() {
        cursor!!.translationX = 0f
        cursor!!.visibility = VISIBLE
        cursor!!.animate()
            .setDuration(600)
            .translationX(layoutPsd!!.width.toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    cursor!!.visibility = INVISIBLE
                    setPSDViewBackgroundResource(correctStatusColor)
                    tvInputTip!!.text = correctInputTip
                    ivLock!!.animate().alpha(0f).scaleX(0f).scaleY(0f).setDuration(500).start()
                    ivOk!!.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(500)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                super.onAnimationEnd(animation)
                                if (listener != null) {
                                    listener!!.onSuccess(passcodeFromView)
                                }
                            }
                        }).start()
                }
            })
            .start()
    }

    /*
     get wrong input values from view
    */
    private val passcodeFromView: String
        get() {
            val sb = StringBuilder()
            val childCount = layoutPsd!!.childCount
            for (i in 0 until childCount) {
                val child = layoutPsd!!.getChildAt(i)
                val num = child.tag as Int
                sb.append(num)
            }
            return sb.toString()
        }

    /**
     * The type for this passcodeView
     */
//    @IntDef([PasscodeViewType.TYPE_SET_PASSCODE, PasscodeViewType.TYPE_CHECK_PASSCODE])
    @Retention(AnnotationRetention.SOURCE)
    annotation class PasscodeViewType {
        companion object {
            /**
             * set passcode, with twice input
             */
            const val TYPE_SET_PASSCODE = 0

            /**
             * check passcode, must pass the result as parameter [PasscodeView.setLocalPasscode]
             */
            const val TYPE_CHECK_PASSCODE = 1
        }
    }

    interface PasscodeViewListener {
        fun onFail(wrongNumber: String?)
        fun onSuccess(number: String?)
    }
}