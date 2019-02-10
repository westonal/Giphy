package io.github.westonal.alansgiphysearch;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;

class DebouncedTextWatcher implements TextWatcher {

    private final Handler handler;
    private final OnChange onChange;
    private final long duration;
    private Runnable runnable;

    public interface OnChange {
        void doThis(String string);
    }

    DebouncedTextWatcher(final OnChange onChange, final long duration) {
        this.onChange = onChange;
        this.duration = duration;
        handler = new Handler();
    }

    @Override
    public void afterTextChanged(Editable s) {
        handler.removeCallbacks(runnable);
        runnable = () -> onChange.doThis(s.toString());
        handler.postDelayed(runnable, duration);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
