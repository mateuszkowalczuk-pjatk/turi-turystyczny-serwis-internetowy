import React, { useEffect, useState } from 'react'
import { imageValidation } from '../../../../utils/imageValidation.ts'
import { imageService } from '../../../../services/imageService.ts'
import { Image, ImageMode } from '../../../../types/image.ts'
import styles from './ImageUploader.module.css'

interface Props {
    uploadImage: (image: Image) => void
    mode: ImageMode
    id?: number
}

const ImageUploader = ({ uploadImage, mode, id }: Props) => {
    const [currentMode, setCurrentMode] = useState(mode)
    const [currentId, setCurrentId] = useState(id)

    useEffect(() => {
        setCurrentMode(mode)
        setCurrentId(id)
    }, [mode, id])

    const handleFileChange = async (event: React.ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files || event.target.files.length === 0) {
            return
        }

        const file = event.target.files[0]

        imageValidation(file)

        const response = await imageService.upload(file, currentMode, currentId)
        if (response.status === 200) {
            const path = await response.text()
            const image: Image = {
                path: path
            }
            uploadImage(image)
        }
    }

    return (
        <div className={styles.uploader}>
            <label className={styles.button}>
                +
                <input
                    id="image-upload"
                    type="file"
                    accept=".png, .jpg, .jpeg"
                    onChange={handleFileChange}
                    className={styles.input}
                />
            </label>
        </div>
    )
}

export default ImageUploader
