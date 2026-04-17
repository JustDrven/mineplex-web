import os as finder
import shutil as file_util

def log(message: str):
    print("[Mineplex Script] " + message)


tempEnvFile = "resources/.env"
modules = [
    "minecraft",
    "staff",
    "forum",
]

log("Trying to manage project modules..")
for module in modules:
    log("Managing " + module + "!")

    envFile = module + "/.env"

    if finder.path.exists(envFile):
        log("Refreshing environment file for '" + module + "'")
        finder.remove(envFile)
        
    file_util.copyfile(tempEnvFile, envFile, follow_symlinks=True)
    log("Successfully copied environment file for '" + module + "'")


log("Copying is complate!")
