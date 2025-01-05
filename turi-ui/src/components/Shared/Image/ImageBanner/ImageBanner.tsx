import { Image, ImageMode } from '../../../../types/image.ts'
import ImageUploader from '../ImageUploader'
import ImagePanel from '../ImagePanel'
import styles from './ImageBanner.module.css'

interface Props {
    images?: Image[]
    uploadImage?: (image: Image) => void
    mode?: ImageMode
    id?: number
    removeImage?: (imageId: number) => void
    files?: File[]
    uploadFile?: (file: File) => void
    removeFile?: (file: File) => void
    presentation?: boolean
}

const ImageBanner = ({
    images,
    uploadImage,
    mode,
    id,
    removeImage,
    files,
    uploadFile,
    removeFile,
    presentation
}: Props) => {
    return (
        <div className={styles.banner}>
            {!presentation && (
                <ImageUploader
                    uploadImage={uploadImage}
                    mode={mode}
                    id={id}
                    uploadFile={uploadFile}
                />
            )}
            {images &&
                images.map((image, index) => (
                    <ImagePanel
                        id={image.imageId}
                        path={image.path}
                        alt={`Image ${index + 1}`}
                        key={image.imageId}
                        removeImage={removeImage}
                        onlyDisplay={presentation}
                    />
                ))}
            {files &&
                files.map((file, index) => (
                    <ImagePanel
                        alt={`File ${index + 1}`}
                        key={index}
                        removeImage={removeImage}
                        file={file}
                        removeFile={removeFile}
                    />
                ))}
        </div>
    )
}

export default ImageBanner
