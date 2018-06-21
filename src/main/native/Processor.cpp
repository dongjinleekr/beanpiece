#include "com_dongjinlee_beanpiece_Processor.h"

#include "sentencepiece_processor.h"

#include <iostream>

JNIEXPORT jlong JNICALL Java_com_dongjinlee_beanpiece_Processor_initialize
  (JNIEnv *, jobject) {
  std::cout << "Hello World from C++!" << std::endl;
  sentencepiece::SentencePieceProcessor* p = new sentencepiece::SentencePieceProcessor();
  p->LoadOrDie("/home/djlee/workspace/java/beanpiece/src/test/resources/test_model.model");
  std::vector<std::string> pieces;
  p->Encode("This is a test.", &pieces);
  for (const std::string &token : pieces) {
    std::cout << token << std::endl;
  }
  delete p;
  return reinterpret_cast<jlong>(42L);
}
