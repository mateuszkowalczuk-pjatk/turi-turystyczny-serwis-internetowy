import React from 'react'
import { imageValidation } from '../../../../utils/imageValidation.ts'
import { imageService } from '../../../../services/imageService.ts'
import { Image, ImageMode } from '../../../../types/image.ts'
import styles from './ImageUploader.module.css'

interface Props {
    uploadImage?: (image: Image) => void
    mode?: ImageMode
    id?: number
    uploadFile?: (file: File) => void
}

const ImageUploader = ({ uploadImage, mode, id, uploadFile }: Props) => {
    const handleFileChange = async (event: React.ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files || event.target.files.length === 0) {
            return
        }

        const file = event.target.files[0]

        imageValidation(file)

        if (uploadFile) uploadFile(file)
        else if (mode) {
            const response = await imageService.upload(file, mode, id)
            if (response.status === 200 && uploadImage) {
                const path = await response.text()
                const image: Image = { path: path }
                uploadImage(image)
            }
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
