package com.example.yuriy.multithreadsorter.service.manager;

import com.example.yuriy.multithreadsorter.model.Mechanizm;

import java.util.List;


public class ThreadManager {
    Mechanizm[][] multiTaskData;
    Mechanizm[] firstTaskData;
    Mechanizm[] secondTaskData;
    Mechanizm[] thirdTaskData;
    ThreadManagerCallback callback;

    public ThreadManager() {
    }

    public static Builder newBuilder() {
        return new ThreadManager().new Builder();
    }

    public void executeMultiTask(int sortTypeMethod) {
        TaskExecutor.executeMultiTask(multiTaskData, callback, sortTypeMethod);
    }

    public interface ThreadManagerCallback {
        void onMultiTaskFinish(List<AdvancedDataLinkedList<Mechanizm>> sortedMultiData);
    }

    public class Builder {

        private Builder() {
        }

        public Builder setMultiTask(Mechanizm[][] multiTaskData) {
            ThreadManager.this.multiTaskData = multiTaskData;
            return this;
        }

        public Builder setFirstTaskData(Mechanizm[] taskDataList) {
            ThreadManager.this.firstTaskData = taskDataList;
            return this;
        }


        public Builder setSecontTaskData(Mechanizm[] taskDataList) {
            ThreadManager.this.secondTaskData = taskDataList;
            return this;
        }

        public Builder setThirdTaskData(Mechanizm[] taskDataList) {
            ThreadManager.this.thirdTaskData = taskDataList;
            return this;
        }

        public Builder registerOnFinishCallback(ThreadManagerCallback callback) {
            ThreadManager.this.callback = callback;
            return this;
        }


        public ThreadManager build() {
            return ThreadManager.this;
        }


    }
}
