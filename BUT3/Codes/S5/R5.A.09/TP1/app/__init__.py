import logging

logging.basicConfig(
  level=logging.INFO,
  handlers=[logging.StreamHandler()],
  format="%(asctime)s - %(levelname)s - %(name)s - %(threadName)s - %(message)s",
)

LOGGER = logging.getLogger(__name__)
logging.getLogger("fastapi").setLevel(logging.INFO)
logging.getLogger("uvicorn").setLevel(logging.INFO)
