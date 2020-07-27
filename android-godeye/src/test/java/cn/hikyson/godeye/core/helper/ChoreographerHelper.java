package cn.hikyson.godeye.core.helper;

import android.view.Choreographer;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ChoreographerHelper {

    public static void setup() {
        Choreographer choreographer = Mockito.spy(Choreographer.getInstance());
        ChoreographerInjecor.setChoreographerProvider(new ChoreographerInjecor.ChoreographerProvider() {
            @Override
            public Choreographer getChoreographer() {
                return choreographer;
            }
        });
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(choreographer).postFrameCallback(Mockito.any());
    }

    public static void teardown() {
        ChoreographerInjecor.setChoreographerProvider(null);
    }
}
