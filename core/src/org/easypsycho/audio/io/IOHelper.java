///*******************************************************************************
// * Copyright (C) 2022 Gancheng Zhu
// * org.easypsycho.EasyPsycho is a Free Software project under the GNU Affero General
// * Public License v3, which means all its code is available for everyone
// * to download, examine, use, modify, and distribute, subject to the usual
// * restrictions attached to any GPL software. If you are not familiar with the AGPL,
// * see the COPYING file for for more details on license terms and other legal issues.
// ******************************************************************************/
//package org.easypsycho.io;
//
///**
// * API for reading/writing csv file{@link IOHelper}
// */
//public class IOHelper {
//
//    /**
//     * Returns a {@link FileHandle} of a file or directory in the player data location
//     * @param filepath The path to the file. This will be resolved as a path within the player data location.
//     * @return A {@link FileHandle} instance for file or directory
//     */
//    public FileHandle getFileHandle(String... filepath){
//        return resolve(filepath);
//    };
//
//    /**
//     * Reads the contents of a file in the player data location into a {@link String}
//     *
//     * @param filepath
//     *            The path to the file. This will be resolved as a path
//     *            within the game data location.
//     * @return A string containing the contents of the file
//     * @throws PlayerDataException
//     *             Thrown if the file does not exist
//     */
//    public String readString(String... filepath) throws PlayerDataException {
//        if (filepath.length == 0) {
//            throw new PlayerDataException("No file path specified");
//        }
//        try {
//            FileHandle file = resolve(filepath);
//            return file.readString();
//        } catch (Exception e) {
//            throw new PlayerDataException(e);
//        }
//    }
//
//    /**
//     * Writes a {@link String} to a file in the player data location
//     *
//     * @param content
//     *            The {@link String} to be written to the file
//     * @param filepath
//     *            The path to the file. This will be resolved as a path
//     *            within the game data location.
//     */
//    public void writeString(String content, String... filepath)
//            throws PlayerDataException {
//        if (filepath.length == 0) {
//            throw new PlayerDataException("No file path specified");
//        }
//        try {
//            ensureDirectoryExistsForFile(filepath);
//            FileHandle file = resolve(filepath);
//            FileHandle tmpFile = resolveTmp(filepath);
//            tmpFile.writeString(content, false);
//            if(file.exists()) {
//                file.delete();
//            }
//            tmpFile.moveTo(file);
//        } catch (Exception e) {
//            throw new PlayerDataException(e);
//        }
//    }
//
//}
