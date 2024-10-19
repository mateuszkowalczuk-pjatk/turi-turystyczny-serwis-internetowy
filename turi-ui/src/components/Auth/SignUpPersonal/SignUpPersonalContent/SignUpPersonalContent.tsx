import { useTranslation } from 'react-i18next'
import { useState } from 'react'
import PersonalPart from '../../../Personal/PersonalPart'
import PersonalPanel from '../../../Personal/PersonalPanel'
import PersonalLabel from '../../../Personal/PersonalLabel'
import PersonalInput from '../../../Personal/PersonalInput'
import PersonalGender from '../../../Personal/PersonalGender'
import SignUpPersonalButtons from '../SignUpPersonalButtons'
import SignUpPersonalSkipOverlay from '../SignUpPersonalSkip/SignUpPersonalSkipOverlay'
import styles from './SignUpPersonalContent.module.css'

const SignUpPersonalContent = () => {
    const { t } = useTranslation();
    const [ isPersonalSkip, setIsPersonalSkip ] = useState(false);

    const handleSkipClick = () => {
        setIsPersonalSkip(true);
    };

    const handleCloseSkip = () => {
        setIsPersonalSkip(false);
    };

    return (
        <>
            <div className={`${styles.content} ${isPersonalSkip ? styles.background : ''}`}>
                <PersonalPart
                    firstPanel={
                        <PersonalPanel
                            label={
                                <PersonalLabel
                                    text={t('signup-personal.name-surname')}
                                />
                            }
                            firstInput={
                                <PersonalInput
                                    text={t('signup-personal.name')}
                                />
                            }
                            secondInput={
                                <PersonalInput
                                    text={t('signup-personal.surname')}
                                />
                            }
                        />
                    }
                    secondPanel={
                        <PersonalPanel
                            label={
                                <PersonalLabel
                                    text={t('signup-personal.birthdate')}
                                />
                            }
                            firstInput={
                                <PersonalInput
                                    text={t('signup-personal.day')}
                                />
                            }
                            secondInput={
                                <PersonalInput
                                    text={t('signup-personal.month')}
                                />
                            }
                            thirdInput={
                                <PersonalInput
                                    text={t('signup-personal.year')}
                                />
                            }
                        />
                    }
                    option={
                        <PersonalPanel
                            label={
                                <PersonalLabel
                                    text={t('signup-personal.gender')}
                                />
                            }
                            firstInput={
                                <PersonalGender />
                            }
                        />
                    }
                />
                <PersonalPart
                    firstPanel={
                        <PersonalPanel
                            label={
                                <PersonalLabel
                                    text={t('signup-personal.phone-number')}
                                />
                            }
                            firstInput={
                                <PersonalInput
                                    text={t('signup-personal.phone-number')}
                                />
                            }
                        />
                    }
                    secondPanel={
                        <PersonalPanel
                            label={
                                <PersonalLabel
                                    text={t('signup-personal.address')}
                                />
                            }
                            firstInput={
                                <PersonalInput
                                    text={t('signup-personal.country')}
                                />
                            }
                            secondInput={
                                <PersonalInput
                                    text={t('signup-personal.city')}
                                />
                            }
                            thirdInput={
                                <PersonalInput
                                    text={t('signup-personal.zipcode')}
                                />
                            }
                            fourthInput={
                                <PersonalInput
                                    text={t('signup-personal.street')}
                                />
                            }
                            fifthInput={
                                <PersonalInput
                                    text={t('signup-personal.building')}
                                />
                            }
                            sixthInput={
                                <PersonalInput
                                    text={t('signup-personal.apartment')}
                                />
                            }
                        />
                    }
                    option={
                        <SignUpPersonalButtons
                            skipOnClick={handleSkipClick}
                        />
                    }
                />
            </div>
            {isPersonalSkip &&
                <SignUpPersonalSkipOverlay
                    onClick={ handleCloseSkip }
                />
            }
        </>
    )
}

export default SignUpPersonalContent;