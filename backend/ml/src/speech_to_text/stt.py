import nemo.collections.asr as nemo_asr

asr_model = nemo_asr.models.EncDecHybridRNNTCTCBPEModel.restore_from(
    "/Users/clarence/Documents/playground/HackBangalore/final-singapore/Nexties-Nextlyf/backend/ml/models/nvidia-stt_en_conformer_ctc_large/stt_en_conformer_ctc_large.nemo", 
)

def transcribe_speech(audio_path):
    # Transcribe speech
    audio_signal = asr_model.featurizer.process_from_file(audio_path)
    transcript = asr_model.transcribe([audio_signal])
    return transcript[0]