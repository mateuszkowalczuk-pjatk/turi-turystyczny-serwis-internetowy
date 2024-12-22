import TextRegular from '../../Shared/Controls/Text/TextRegular'
import styles from './AuthDownLink.module.css'

interface Props {
    firstLink: string
    secondLink: string
    firstOnClick: () => void
    secondOnClick?: () => void
}

const AuthDownLink = ({ firstLink, secondLink, firstOnClick, secondOnClick }: Props) => {
    const isRight = secondLink === 'right'
    const isCenter = secondLink === 'center'

    return (
        <div className={`${styles.link} ${isCenter ? styles.linkCenter : ''} ${isRight ? styles.linkRight : ''}`}>
            {!isRight && !isCenter ? (
                <>
                    <TextRegular
                        text={firstLink}
                        onClick={firstOnClick}
                    />
                    <TextRegular
                        text={secondLink}
                        onClick={secondOnClick}
                    />
                </>
            ) : (
                <TextRegular
                    text={firstLink}
                    onClick={firstOnClick}
                />
            )}
        </div>
    )
}

export default AuthDownLink
