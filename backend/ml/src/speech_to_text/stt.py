import torch
import nemo.collections.asr as nemo_asr

asr_model = nemo_asr.models.EncDecHybridRNNTCTCBPEModel.restore_from(
    "/Users/clarence/Documents/playground/HackBangalore/final-singapore/Nexties-Nextlyf/backend/ml/models/nvidia-stt_en_conformer_ctc_large/stt_en_conformer_ctc_large.nemo",
    # map_location=torch.device("cpu")
)
# asr_model = nemo_asr.models.ASRModel.from_pretrained(
#     restore_from="/Users/clarence/Documents/playground/HackBangalore/final-singapore/Nexties-Nextlyf/backend/ml/models/nvidia-stt_en_conformer_ctc_large/stt_en_conformer_ctc_large.nemo",
# )

def transcribe_speech(audio_path=None, audio_data=None):
    global asr_model
    
    if audio_path is not None:
        audio_data = asr_model.featurizer.process_from_file(audio_path)

    transcript = asr_model.transcribe([audio_data])
    return transcript[0]

# if __name__ == '__main__':
#     import pyaudio





    