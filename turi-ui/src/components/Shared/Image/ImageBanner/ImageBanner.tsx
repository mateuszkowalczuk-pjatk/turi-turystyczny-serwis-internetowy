import { Image, ImageMode } from '../../../../types/image.ts'
import ImageUploader from '../ImageUploader'
import ImagePanel from '../ImagePanel'
import styles from './ImageBanner.module.css'

interface Props {
    images: Image[]
    uploadImage: (image: Image) => void
    mode: ImageMode
    id?: number
    removeImage: (imageId: number) => void
}

const ImageBanner = ({ images, uploadImage, mode, id, removeImage }: Props) => {
    return (
        <div className={styles.banner}>
            <ImageUploader
                uploadImage={uploadImage}
                mode={mode}
                id={id}
            />
            {images.map((image, index) => (
                <ImagePanel
                    id={image.imageId}
                    path={image.path}
                    alt={`Image ${index + 1}`}
                    key={image.imageId}
                    removeImage={removeImage}
                />
            ))}
        </div>
    )
}

export default ImageBanner
