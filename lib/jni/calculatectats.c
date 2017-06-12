#include "calculatectats.h"

#include <jni.h>
#include <math.h>

JNIEXPORT jfloat JNICALL Java_ra57_12014_pnrs1_rtrk_taskmanager_statistics_NativeCalculation_calculateAngleOfAnimation
  (JNIEnv *env, jobject obj, jint total, jint completed)
{
    if(total == 0)
    {
        return 0;
    }
    return (jfloat) floor(((double)completed/(double)total)*360);
}
