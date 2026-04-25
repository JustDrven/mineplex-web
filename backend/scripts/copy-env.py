import os as finder
import shutil as file_util

import include.module as module_system
import include.logger as logger

tempEnvFile = "resources/.env"


logger.log("Trying to manage project modules..")
for module in module_system.modules:
    logger.log("Managing " + module + "!")

    envFile = module + "/.env"

    if finder.path.exists(envFile):
        logger.log("Refreshing environment file for '" + module + "'")
        finder.remove(envFile)
        
    file_util.copyfile(tempEnvFile, envFile, follow_symlinks=True)
    logger.log("Successfully copied environment file for '" + module + "'")


logger.log("Copying is complate!")
