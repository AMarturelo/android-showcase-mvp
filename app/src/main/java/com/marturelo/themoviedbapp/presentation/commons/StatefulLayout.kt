package com.marturelo.themoviedbapp.presentation.commons
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.core.view.isGone

class StatefulLayout : FrameLayout {
    private val mStateViews: MutableMap<String, View> =
            HashMap()
    private var mState: String? = null
    private var mOnStateChangeListener: OnStateChangeListener? = null
    private var mInitialized = false
    private var mDirtyFlag = false

    interface OnStateChangeListener {
        fun onStateChange(state: String)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(
            context,
            attrs
    ) {
    }

    constructor(
            context: Context,
            attrs: AttributeSet,
            defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (!mInitialized) onSetupContentState()
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(
                SAVED_INSTANCE_STATE,
                super.onSaveInstanceState()
        )
        saveInstanceState(bundle)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        var temp: Parcelable? = state
        if (temp is Bundle) {
            val bundle = temp
            if (mState == null) restoreInstanceState(bundle)
            temp = bundle.getParcelable(SAVED_INSTANCE_STATE)
        }
        super.onRestoreInstanceState(temp)
    }

    fun setStateView(state: String, view: View) {
        if (mStateViews.containsKey(state)) {
            removeView(mStateViews[state])
        }
        mStateViews[state] = view
        if (view.parent == null) {
            addView(view)
        }
        view.visibility = View.GONE
        mDirtyFlag = true
    }

    var state: String?
        get() = mState
        set(newState) {
            checkNotNull(newState)
            checkNotNull(getStateView(newState)) {
                String.format(
                        "Cannot switch to state \"%s\". This state was not defined or the view for this state is null.",
                        state
                )
            }
            if (mState != null && mState == newState && !mDirtyFlag) return
            mState = newState
            mStateViews.keys.forEach { oldState ->
                mStateViews[oldState]?.isGone = oldState != newState
            }
            mOnStateChangeListener?.onStateChange(newState)
            mDirtyFlag = false
        }

    fun setOnStateChangeListener(listener: OnStateChangeListener?) {
        mOnStateChangeListener = listener
    }

    fun removeStateChangeListener() {
        mOnStateChangeListener = null
    }

    fun saveInstanceState(outState: Bundle) {
        mState?.let {
            outState.putString(SAVED_STATE, it)
        }
    }

    fun restoreInstanceState(savedInstanceState: Bundle): String? {
        val savedState =
                savedInstanceState.getString(SAVED_STATE)
        state = savedState
        return state
    }

    fun getStateView(state: String): View? {
        return mStateViews[state]
    }

    fun clearStates() {
        for (state in HashSet(mStateViews.keys)) {
            val view = mStateViews[state]
            if (state != State.CONTENT) {
                removeView(view)
                mStateViews.remove(state)
            }
        }
    }

    fun setStateController(stateController: StateController) {
        clearStates()
        for (state in stateController.states.keys) {
            setStateView(state, stateController.states.getValue(state))
        }
        stateController.setOnStateChangeListener(object : OnStateChangeListener {
            override fun onStateChange(state: String) {
                this@StatefulLayout.state = state
            }
        })
        state = stateController.state
    }

    @CallSuper
    protected fun onSetupContentState() {
        check(childCount == 1 + mStateViews.size) { "Invalid child count. StatefulLayout must have exactly one child." }
        val contentView = getChildAt(mStateViews.size)
        removeView(contentView)
        setStateView(
                State.CONTENT,
                contentView
        )
        state = State.CONTENT
        mInitialized = true
    }

    object State {
        const val CONTENT = "content"
    }

    class StateController private constructor() {
        private val mStateMap: MutableMap<String, View> =
                HashMap()
        private var mState =
                State.CONTENT
        private var mListener: OnStateChangeListener? = null
        val states: Map<String, View>
            get() = mStateMap

        var state: String
            get() = mState
            set(newState) {
                mState = newState
                mListener?.onStateChange(newState)
            }

        fun setOnStateChangeListener(listener: OnStateChangeListener?) {
            mListener = listener
        }

        class Builder {
            var mStateController = StateController()
            fun withState(
                    state: String,
                    stateView: View
            ): Builder {
                mStateController.mStateMap[state] = stateView
                return this
            }

            fun build(): StateController {
                return mStateController
            }
        }

        companion object {
            fun create(): Builder {
                return Builder()
            }
        }
    }

    companion object {
        const val SAVED_INSTANCE_STATE = "instanceState"
        private const val SAVED_STATE = "stateful_layout_state"
    }
}