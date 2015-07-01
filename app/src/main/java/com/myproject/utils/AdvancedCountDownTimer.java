package com.myproject.utils;

import android.os.Handler;
import android.os.Message;

public abstract class AdvancedCountDownTimer {
	private final long mCountdownInterval;
	private long mTotalTime;
	private long mRemainTime;

	private static final int MSG_RUN = 1;
	private static final int MSG_PAUSE = 2;

	public AdvancedCountDownTimer(long millisInFuture, long countDownInterval) {
		super();
		this.mCountdownInterval = countDownInterval;
		this.mTotalTime = millisInFuture;
		this.mRemainTime = millisInFuture;
	}

	private final void seek(int value) {
		synchronized (AdvancedCountDownTimer.this) {
			mRemainTime = ((100 - value) * mTotalTime) / 100;
		}
	}

	public final void cancel() {
		mHandler.removeMessages(MSG_RUN);
		mHandler.removeMessages(MSG_PAUSE);
	}

	public final void pause() {
		mHandler.removeMessages(MSG_RUN);
		mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(MSG_PAUSE));
	}

	public final void resume() {
		mHandler.removeMessages(MSG_PAUSE);
		mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(MSG_RUN));
	}

	public abstract void onTick(long millisUntilFinished, int percent);

	public abstract void onFinish();

	private Handler mHandler = new Handler() {

		@Override
		public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
			synchronized (AdvancedCountDownTimer.this) {
				if (msg.what == MSG_RUN) {
					mRemainTime = mRemainTime - mCountdownInterval;

					if (mRemainTime <= 0) {
						onFinish();
					} else if (mRemainTime < mCountdownInterval) {
						sendMessageDelayed(obtainMessage(MSG_RUN), mRemainTime);
					} else {
						onTick(mRemainTime, new Long(100 * (mTotalTime - mRemainTime) / mTotalTime).intValue());
						sendMessageDelayed(obtainMessage(MSG_RUN), mCountdownInterval);
					}
				} else if (msg.what == MSG_PAUSE) {

				}
			}
			return super.sendMessageAtTime(msg, uptimeMillis);
		}

	};
}
